package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices

import com.akaun.kmp.sdk.models.aggregates.erp.pickpackqueue.FinanciaPickPackQueueModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.JobsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object Core2UrlJobsDocPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "del/job-docs"
}

class JobsDocService(private val client: HttpClient) {

    suspend fun cancelJobLoginDriver(JobsDto: JobsDto): ApiResponseModel<FinanciaPickPackQueueModel> =
        client.post(Core2UrlJobsDocPrefix.PREFIX + "/cancel-job/login-driver-ep") {
            setBody(JobsDto)
        }.body()
}
