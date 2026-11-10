package com.akaun.kmp.sdk.services.comakaunapi.core2.criteria

import kotlinx.serialization.Serializable

@Serializable
data class TripsPostQueryCriteria(
    var label_list_guids: Set<String?>? = null,
    var created_by_subject_guids: Set<String?>? = null,
    var updated_by_subject_guids: Set<String?>? = null,
    var guid_driver_hdr: Set<String?>? = null,
    var guid_vehicle_hdr: Set<String?>? = null,
    var process_hdr_guids: Set<String?>? = null,
    var process_status_guids: Set<String?>? = null,
    var process_resolution_guids: Set<String?>? = null,
    var trip_guids: Set<String?>? = null,
    var trip_start_date_from: String? = null,
    var trip_start_date_to: String? = null,
    var trip_end_date_from: String? = null,
    var trip_end_date_to: String? = null,
    var date_created_from: String? = null,
    var date_created_to: String? = null,
    var search_keyword: String? = null,
    var orderBy : String? = null,
    var order : String? = null
)
