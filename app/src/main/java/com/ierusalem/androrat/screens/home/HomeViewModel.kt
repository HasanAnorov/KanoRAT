package com.ierusalem.androrat.screens.home

import android.graphics.Bitmap
import android.graphics.Picture
import android.os.Build
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private var _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    fun updatePhoto(picture: Picture) {
        _state.update {
            it.copy(
                screenshot = createBitmapFromPicture(picture)
            )
        }
    }

    private fun createBitmapFromPicture(picture: Picture): Bitmap{
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Bitmap.createBitmap(picture)
        } else {
            val bitmap = Bitmap.createBitmap(
                picture.width,
                picture.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            canvas.drawColor(android.graphics.Color.WHITE)
            canvas.drawPicture(picture)
            bitmap
        }
        return bitmap
    }

}

@Immutable
data class HomeScreenState(
    val screenshot: Bitmap? = null
)