package com.ierusalem.androrat.features.webrtc

import io.getstream.log.taggedLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class SignalingClient {
  private val logger by taggedLogger("Call:SignalingClient")
  private val signalingScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  private val client = OkHttpClient()
  private val request = Request
    .Builder()
    .url("ws://10.128.105.70:8080/rtc")
    .build()

  // opening web socket with signaling server
  private val ws = client.newWebSocket(request, SignalingWebSocketListener())

  // session flow to send information about the session state to the subscribers
  private val _sessionStateFlow = MutableStateFlow(WebRTCSessionState.Offline)
  val sessionStateFlow: StateFlow<WebRTCSessionState> = _sessionStateFlow

  // signaling commands to send commands to value pairs to the subscribers
  private val _signalingCommandFlow = MutableSharedFlow<Pair<SignalingCommand, String>>()
  val signalingCommandFlow: SharedFlow<Pair<SignalingCommand, String>> = _signalingCommandFlow

  fun sendCommand(signalingCommand: SignalingCommand, message: String) {
    logger.d { "[sendCommand] $signalingCommand $message" }
    ws.send("$signalingCommand $message")
  }

  private inner class SignalingWebSocketListener : WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String) {
      when {
        text.startsWith(SignalingCommand.STATE.toString(), true) ->
          handleStateMessage(text)
        text.startsWith(SignalingCommand.OFFER.toString(), true) ->
          handleSignalingCommand(SignalingCommand.OFFER, text)
        text.startsWith(SignalingCommand.ANSWER.toString(), true) ->
          handleSignalingCommand(SignalingCommand.ANSWER, text)
        text.startsWith(SignalingCommand.ICE.toString(), true) ->
          handleSignalingCommand(SignalingCommand.ICE, text)
      }
    }
  }

  private fun handleStateMessage(message: String) {
    val state = getSeparatedMessage(message)
    _sessionStateFlow.value = WebRTCSessionState.valueOf(state)
  }

  private fun handleSignalingCommand(command: SignalingCommand, text: String) {
    val value = getSeparatedMessage(text)
    logger.d { "received signaling: $command $value" }
    signalingScope.launch {
      _signalingCommandFlow.emit(command to value)
    }
  }

  private fun getSeparatedMessage(text: String) = text.substringAfter(' ')

  fun dispose() {
    _sessionStateFlow.value = WebRTCSessionState.Offline
    signalingScope.cancel()
    ws.cancel()
  }
}

enum class WebRTCSessionState {
  Active, // Offer and Answer messages has been sent
  Creating, // Creating session, offer has been sent
  Ready, // Both clients available and ready to initiate session
  Impossible, // We have less than two clients connected to the server
  Offline // unable to connect signaling server
}

enum class SignalingCommand {
  STATE, // Command for WebRTCSessionState
  OFFER, // to send or receive offer
  ANSWER, // to send or receive answer
  ICE // to send and receive ice candidates
}
