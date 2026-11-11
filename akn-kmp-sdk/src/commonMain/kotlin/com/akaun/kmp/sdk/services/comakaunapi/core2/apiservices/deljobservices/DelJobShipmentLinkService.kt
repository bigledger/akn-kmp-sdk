package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices

import com.akaun.kmp.sdk.models.aggregates.erp.delshipment.DelJobShipmentLinkModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

object Core2UrlDelJobShipmentLinkPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "del/job-shipment-links"
}

class DelJobShipmentLinkService(private val client: HttpClient) {

    suspend fun getJobShipmentLinkByCriteriaLoginDriver(
        calcTotalRecords: Boolean? = true,
        orderBy: String? = null,
        limit: String? = null,
        jobHdrGuids: Set<String>? = null,
    ): PagingResponseModel<DelJobShipmentLinkModel> =
        client.get(Core2UrlDelJobShipmentLinkPrefix.PREFIX + "/login-driver-ep/query") {
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("orderBy", orderBy)
            parameter("limit", limit)
            // Repeated keys like Retrofit (job_hdr_guids=a&job_hdr_guids=b); parameter() would send "[a, b]"
            jobHdrGuids?.let { url.parameters.appendAll("job_hdr_guids", it) }
        }.body()
}
