package com.ierusalem.androrat.features.home.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.emitNavigation
import com.ierusalem.androrat.features.images.domain.Image
import com.ierusalem.androrat.features.home.presentation.HomeScreenNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel(),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    private var _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _drawerShouldBeOpened = MutableStateFlow(false)
    val drawerShouldBeOpened = _drawerShouldBeOpened.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    private var images by mutableStateOf(emptyList<Image>())

    private fun openDrawer(){
        _drawerShouldBeOpened.value = true
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }

    fun updateImages(images: List<Image>) {
        this.images = images
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeAt(0)
    }

    fun updatePermissionState(permission: String, isGranted: Boolean) {
        val oldPermissionsState = state.value.permissions
        oldPermissionsState[permission] = isGranted
        _state.update {
            it.copy(
                permissions = oldPermissionsState
            )
        }
    }

    fun handleDrawerAction(action: DrawerClicks) {
        when (action) {

            DrawerClicks.NavigateToLinkPhishing -> {
                emitNavigation(HomeScreenNavigation.NavigateToLinkPhishingFragment)
            }

            DrawerClicks.NavigateToAndroRtc -> {
                emitNavigation(HomeScreenNavigation.NavigateToAndroRtcFragment)
            }

            DrawerClicks.NavigateToSettings -> {
                emitNavigation(HomeScreenNavigation.NavigateToSettingsFragment)
            }
        }
    }

    fun handleEvents(event: HomeScreenClickIntents) {
        when (event) {
            HomeScreenClickIntents.OpenDrawer -> {
                openDrawer()
            }

            HomeScreenClickIntents.NavigateToMessageFragment -> {

            }

            HomeScreenClickIntents.SaveScreenshotClick -> {

            }

            HomeScreenClickIntents.NavigateToImagesAndVideos -> {

            }
        }
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

}
