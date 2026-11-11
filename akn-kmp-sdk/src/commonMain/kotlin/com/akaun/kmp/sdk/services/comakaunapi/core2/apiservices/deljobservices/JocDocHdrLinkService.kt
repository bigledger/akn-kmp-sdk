package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices

import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobDocHdrLinkModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

object Core2UrlJobDocHdrLinkPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "del/job-dochdr-links"
}

class JobDocHdrLinkService(private val client: HttpClient) {

    suspend fun getJobDocHdrLinkByCriteria(
        jobHdrGuid: String,
        calcTotalRecords: Boolean,
        order: String,
        limit: Int,
        offset: Int
    ): PagingResponseModel<JobDocHdrLinkModel> =
        client.get(Core2UrlJobDocHdrLinkPrefix.PREFIX + "/query") {
            parameter("hdr_guid", jobHdrGuid)
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("order", order)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
}
