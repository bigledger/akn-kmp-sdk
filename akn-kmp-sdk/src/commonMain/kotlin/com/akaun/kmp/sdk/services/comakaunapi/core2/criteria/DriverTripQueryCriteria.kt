package com.akaun.kmp.sdk.services.comakaunapi.core2.criteria

import kotlinx.serialization.Serializable

@Serializable
data class DriverTripQueryCriteria(
    // trip hdr
    var guid_driver_hdr: String? = null,
    var guid_vehicle_hdr: String? = null,
    var guid_region_hdr: String? = null,
    var running_number_01: String? = null,
    var running_number_02: String? = null,
    var running_number_03: String? = null,
    var delivery_status: String? = null,
    var delivery_logic: String? = null,
    var process_hdr_guid: String? = null,
    var process_status_guid: String? = null,
    var process_status_name: String? = null,
    var process_resolution_guid: String? = null,
    var process_resolution_code: String? = null,
    var delivery_region_code: String? = null,
    var driver_name: String? = null,
    var vehicle_reg_plate: String? = null,
    var code: String? = null,
    var name: String? = null
)
