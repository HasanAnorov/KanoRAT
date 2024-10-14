package com.ierusalem.androrat.features.link_phishing.presentation

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
import com.ierusalem.androrat.core.utils.executeWithLifecycle
import com.ierusalem.androrat.features.link_phishing.domain.LinkPhishingViewModel

class LinkPhishingFragment : Fragment() {

    private val viewModel: LinkPhishingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                AndroRATTheme {
                    LinkPhishingScreen(
                        uiState = uiState,
                        eventHandler = viewModel::onEvent
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

    private fun executeNavigation(navigation: LinkPhishingNavigation) {
        when(navigation){
            LinkPhishingNavigation.OnNavIconClick -> {
                findNavController().navigateUp()
            }
        }
    }

}