package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.serialnumberservices

import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.SerialNumberModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.SerialNumberValidationDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.SerialNumberValidationOutputDto
import com.akaun.kmp.sdk.utils.wrapper.ApiResult
import com.akaun.kmp.sdk.utils.wrapper.apiResultOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object Core2UrlSerialNumberPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.INV_MODULE_PREFIX + "serial-numbers/"
}

class SerialNumberService(private val client: HttpClient) {

    /** Was Sandwich ApiResponse in the old SDK: errors are returned as [ApiResult], not thrown. */
    suspend fun validateSerialNumbers(
        serialNumberValidationDto: SerialNumberValidationDto
    ): ApiResult<BasicApiResponseModel<SerialNumberValidationDto>> = apiResultOf {
        client.post(Core2UrlSerialNumberPrefix.PREFIX + "multi-validation") {
            setBody(serialNumberValidationDto)
        }.body()
    }

    suspend fun validateSerialNumberList(
        serialNumberValidationDto: SerialNumberValidationDto
    ): BasicApiResponseModel<SerialNumberValidationOutputDto> =
        client.post(Core2UrlSerialNumberPrefix.PREFIX + "validation") {
            setBody(serialNumberValidationDto)
        }.body()

    /** Was Sandwich ApiResponse in the old SDK: errors are returned as [ApiResult], not thrown. */
    suspend fun getSerialNumbersByCriteria(
        sn_id: String = "",
        sn_ids: Set<String> = emptySet(),
        hdr_item_guid: String = "",
        location_guid: String = "",
        quantity_balance_greater_than: Int = 0,
        calcTotalRecords: Boolean = true,
        limit: Int = 100,
        offset: Int = 0
    ): ApiResult<PagingResponseModel<SerialNumberModel>> = apiResultOf {
        client.get(Core2UrlSerialNumberPrefix.PREFIX + "query") {
            parameter("sn_id", sn_id)
            url.parameters.appendAll("sn_ids", sn_ids) // repeated keys like Retrofit
            parameter("hdr_item_guid", hdr_item_guid)
            parameter("location_guid", location_guid)
            parameter("quantity_balance_greater_than", quantity_balance_greater_than)
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
    }
}
