package com.ierusalem.androrat.screens.home

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.ui.navigation.NavigationEventDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel(),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    private var _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun updatePermissionState(permission: String, isGranted: Boolean){
        val oldPermissionsState = state.value.permissions
        oldPermissionsState[permission] = isGranted
        _state.update {
            it.copy(
                permissions = oldPermissionsState
            )
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

    fun updatePhoto(bitmap: ImageBitmap) {
        _state.update {
            it.copy(
                screenshot = bitmap
            )
        }
    }

}

@Immutable
data class HomeScreenState(
    val screenshot: ImageBitmap? = null,
    val permissions : MutableMap<String, Boolean> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) mutableMapOf(
        Manifest.permission.RECORD_AUDIO to false,
        Manifest.permission.CALL_PHONE to false,
        Manifest.permission.READ_MEDIA_IMAGES to false,
        Manifest.permission.READ_MEDIA_AUDIO to false,
        Manifest.permission.CAMERA to false,
        Manifest.permission.READ_SMS to false,
    ) else mutableMapOf(
        Manifest.permission.RECORD_AUDIO to false,
        Manifest.permission.CALL_PHONE to false,
        Manifest.permission.READ_EXTERNAL_STORAGE to false,
        Manifest.permission.CAMERA to false,
        Manifest.permission.READ_SMS to false,
    )
)