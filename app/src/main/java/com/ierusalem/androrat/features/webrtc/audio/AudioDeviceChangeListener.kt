package com.ierusalem.androrat.features.webrtc.audio

typealias AudioDeviceChangeListener = (
    audioDevices: List<AudioDevice>,
    selectedAudioDevice: AudioDevice?
) -> Unit
