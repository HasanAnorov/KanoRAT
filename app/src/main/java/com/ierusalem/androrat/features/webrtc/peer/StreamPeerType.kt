package com.ierusalem.androrat.features.webrtc.peer

/**
 * The type of peer connections, either a [PUBLISHER] that sends data to the call or a [SUBSCRIBER]
 * that receives and decodes the data from the server.
 */
enum class StreamPeerType {
  PUBLISHER,
  SUBSCRIBER
}
