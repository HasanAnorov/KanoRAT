package com.ierusalem.androrat.features.link_phishing.data

import com.ierusalem.androrat.features.link_phishing.data.model.EventsModel
import retrofit2.Response

interface LinkPhishingRepository {

    suspend fun getLinkedDevices(): Response<EventsModel>

}