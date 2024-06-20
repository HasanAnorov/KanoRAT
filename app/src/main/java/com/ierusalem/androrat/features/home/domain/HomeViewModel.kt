package com.ierusalem.androrat.features.home.domain

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.features.home.presentation.HomeScreenNavigation
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel(),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    private var _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    private var images by mutableStateOf(emptyList<Image>())

    fun updateImages(images: List<Image>){
        this.images = images
    }


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
data class Image(
    val id: Long,
    val author: Long,
    val displayName: String,
    val data: String,
    val dataTaken: Long,
    val description: Long,
    val uri: Uri
)