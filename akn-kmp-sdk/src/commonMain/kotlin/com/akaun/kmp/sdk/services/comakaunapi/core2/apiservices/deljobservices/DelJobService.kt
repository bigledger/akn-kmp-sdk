package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices

import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.JobHdrListingQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.JobsDeliveryStatusDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.encodeURLPathPart

object Core2UrlJobPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "del/jobs"
}

class DelJobService(private val client: HttpClient) {

    suspend fun getJobs(
        calcTotalRecords: Boolean,
        orderBy: String,
        order: String,
        tripGuid: String,
        limit: Int,
        offset: Int
    ): PagingResponseModel<JobModel> =
        client.get(Core2UrlJobPrefix.PREFIX + "/backoffice-ep/query") {
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("orderBy", orderBy)
            parameter("order", order)
            parameter("guid_trip_hdr", tripGuid)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getJobHdrListingForLoginDriverByPost(
        jobHdrListingQueryCriteria: JobHdrListingQueryCriteria
    ): ApiResponseModel<JobModel> =
        client.post(Core2UrlJobPrefix.PREFIX + "/login-driver-ep/job-hdr-listings/query") {
            setBody(jobHdrListingQueryCriteria)
        }.body()

    suspend fun completeJobsLoginDriverEp(guid: String): BasicApiResponseModel<JobsDeliveryStatusDto> =
        client.post(Core2UrlJobPrefix.PREFIX + "/login-driver-ep/complete-jobs/" + guid.encodeURLPathPart()).body()

    suspend fun updateJob(jobModel: JobModel): BasicApiResponseModel<JobModel> =
        client.put(Core2UrlJobPrefix.PREFIX) {
            setBody(jobModel)
        }.body()
}
