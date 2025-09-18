package com.ierusalem.androrat.core.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun <VM, EVENT> VM.emitNavigation(event: EVENT) where VM: NavigationEventDelegate<EVENT>, VM : ViewModel{
    viewModelScope.launch { sendEvent(event) }
}

