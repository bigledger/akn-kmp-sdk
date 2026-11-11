package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.delshipmentservices

import com.akaun.kmp.sdk.models.dbschema.bl_del_shipment_processing_open_queue
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.JobsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object Core2UrlDelShipmentHdrPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "delivery-shipment-hdr"
}

class DelShipmentHdrService(private val client: HttpClient) {

    suspend fun cancelJobsFromShipmentLoginDriverEp(jobsDto: JobsDto): ApiResponseModel<bl_del_shipment_processing_open_queue> =
        client.post(Core2UrlDelShipmentHdrPrefix.PREFIX + "/login-driver-ep/cancel-jobs-from-shipment") {
            setBody(jobsDto)
        }.body()
}
