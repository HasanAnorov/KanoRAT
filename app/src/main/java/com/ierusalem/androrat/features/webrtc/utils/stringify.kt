package com.ierusalem.androrat.features.webrtc.utils

import com.ierusalem.androrat.features.webrtc.peer.StreamPeerType
import org.webrtc.IceCandidateErrorEvent
import org.webrtc.MediaStreamTrack
import org.webrtc.SessionDescription
import org.webrtc.audio.JavaAudioDeviceModule

fun SessionDescription.stringify(): String =
  "SessionDescription(type=$type, description=$description)"

fun MediaStreamTrack.stringify(): String {
  return "MediaStreamTrack(id=${id()}, kind=${kind()}, enabled: ${enabled()}, state=${state()})"
}

fun IceCandidateErrorEvent.stringify(): String {
  return "IceCandidateErrorEvent(errorCode=$errorCode, $errorText, address=$address, port=$port, url=$url)"
}

fun JavaAudioDeviceModule.AudioSamples.stringify(): String {
  return "AudioSamples(audioFormat=$audioFormat, channelCount=$channelCount" +
    ", sampleRate=$sampleRate, data.size=${data.size})"
}

fun StreamPeerType.stringify() = when (this) {
  StreamPeerType.PUBLISHER -> "publisher"
  StreamPeerType.SUBSCRIBER -> "subscriber"
}
