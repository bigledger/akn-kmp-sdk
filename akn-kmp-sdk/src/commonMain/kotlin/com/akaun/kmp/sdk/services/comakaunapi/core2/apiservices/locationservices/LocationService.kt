package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.locationservices

import com.akaun.kmp.sdk.models.aggregates.erp.location.LocationModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLPathPart

class LocationService(private val client: HttpClient) {

    suspend fun getLocationByGuid(guid: String): BasicApiResponseModel<LocationModel> =
        client.get(Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + "locations/" + guid.encodeURLPathPart()).body()

    suspend fun getLocationAll(): ApiResponseModel<LocationModel> =
        client.get(Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + "locations").body()
}
