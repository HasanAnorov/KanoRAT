package com.ierusalem.androrat.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun <VM, EVENT> VM.emitNavigation(event: EVENT) where VM: NavigationEventDelegate<EVENT>, VM : ViewModel{
    viewModelScope.launch { sendEvent(event) }
}

suspend fun <VM, EVENT> VM.navigateTo(event: EVENT) where VM: NavigationEventDelegate<EVENT>, VM : ViewModel{
    sendEvent(event)
}