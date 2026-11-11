package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.tripservices

import com.akaun.kmp.sdk.models.aggregates.erp.trip.TripsDriverVehicleModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.DriverTripQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.TripsPostQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.TripListingWithDriverOutputDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object Core2UrlTripPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "del/trips"
}

class TripService(private val client: HttpClient) {

    suspend fun getDriverTrips(
        DriverTripQueryCriteria: DriverTripQueryCriteria
    ): PagingResponseModel<TripListingWithDriverOutputDTO> =
        client.post(Core2UrlTripPrefix.PREFIX + "/backoffice-ep/driver-trip-listing/query") {
            setBody(DriverTripQueryCriteria)
        }.body()

    suspend fun getDriverTripsLoginDriverEp(
        TripsPostQueryCriteria: TripsPostQueryCriteria
    ): PagingResponseModel<TripsDriverVehicleModel> =
        client.post(Core2UrlTripPrefix.PREFIX + "/login-driver-ep/getTrip/query") {
            setBody(TripsPostQueryCriteria)
        }.body()
}
