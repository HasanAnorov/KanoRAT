package com.ierusalem.androrat.features.link_phishing.data

import com.ierusalem.androrat.features.link_phishing.data.model.EventsModel
import retrofit2.Response
import retrofit2.http.GET

interface LinkPhishingService {

    @GET("device/data/list/")
    suspend fun getLinkedDevices(): Response<EventsModel>

}