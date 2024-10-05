package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.AndroRtcInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AndroRtcInfoFragment : Fragment() {

    private val viewModel: AndroRtcInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.state.collectAsStateWithLifecycle()

                AndroRATTheme {
                    AndroRtcInfoScreen(
                        uiState = uiState,
                        onNavIconClick = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }

}