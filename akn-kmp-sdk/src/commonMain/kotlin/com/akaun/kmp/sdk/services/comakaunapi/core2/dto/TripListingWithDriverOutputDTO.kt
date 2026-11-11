package com.akaun.kmp.sdk.services.comakaunapi.core2.dto

import com.akaun.kmp.sdk.models.dbschema.bl_del_driver_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_del_region_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_del_trip_driver_link
import com.akaun.kmp.sdk.models.dbschema.bl_del_trip_hdr
import kotlinx.serialization.Serializable

@Serializable
data class TripListingWithDriverOutputDTO(
    // Backend sends List<List<...>> (one driver list per trip-driver link); the old List<...> failed on every trip with a driver
    val bl_del_driver_hdrs: List<List<bl_del_driver_hdr>>,
    val bl_del_region_hdrs: List<bl_del_region_hdr>,
    val bl_del_trip_driver_links: List<bl_del_trip_driver_link>,
    val bl_del_trip_hdr: bl_del_trip_hdr
)
