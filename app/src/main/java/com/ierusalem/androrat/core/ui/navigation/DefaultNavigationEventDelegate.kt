package com.ierusalem.androrat.core.ui.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class DefaultNavigationEventDelegate<T> : NavigationEventDelegate<T> {
    private val screenNavigationChannel = Channel<T>(Channel.BUFFERED)
    override val screenNavigation = screenNavigationChannel.receiveAsFlow()

    override suspend fun sendEvent(navigation: T) {
        screenNavigationChannel.send(navigation)
    }
}