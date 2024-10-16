package com.ierusalem.androrat.features.link_phishing.data

import com.ierusalem.androrat.features.link_phishing.data.model.EventsModel
import retrofit2.Response

class LinkPhishingRepoImpl(
    private val linkPhishingService: LinkPhishingService
): LinkPhishingRepository {

    override suspend fun getLinkedDevices(): Response<EventsModel> {
        return linkPhishingService.getLinkedDevices()
    }

}