package com.ierusalem.androrat.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ierusalem.androrat.ui.theme.AndroRATTheme

class HomeFragment: Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state by homeViewModel.state.collectAsState()
                AndroRATTheme {
                    HomeScreen(
                        state = state,
                        onReadExternalStoragePermissionRequest = {},
                        onCameraPermissionRequest = {},
                        onRecordAudioPermissionRequest = {},
                        onMultiplePermissionRequest = {},
                        onTakeScreenShotClick = {},
                    )
                }
            }
        }
    }

}