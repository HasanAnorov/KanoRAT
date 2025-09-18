package com.ierusalem.androrat.core.ui.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationEventDelegate<T> {
    val screenNavigation: Flow<T>
    suspend fun sendEvent(navigation: T)
}