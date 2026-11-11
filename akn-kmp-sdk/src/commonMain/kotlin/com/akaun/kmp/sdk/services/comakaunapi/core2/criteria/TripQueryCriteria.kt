package com.akaun.kmp.sdk.services.comakaunapi.core2.criteria

import kotlinx.serialization.Serializable

@Serializable
data class TripQueryCriteria (
    val label_list_guids: Set<String>? = null,
    val created_by_subject_guids: Set<String>? = null,
    val updated_by_subject_guids: Set<String>? = null,
    val guid_driver_hdr: Set<String>? = null,
    val guid_vehicle_hdr: Set<String>? = null,
    val process_hdr_guids: Set<String>? = null,
    val process_status_guids: Set<String>? = null,
    val process_resolution_guids: Set<String>? = null,
    val trip_guids: Set<String>? = null,
    val trip_start_date_from: String? = null,
    val trip_start_date_to: String? = null,
    val trip_end_date_from: String? = null,
    val trip_end_date_to: String? = null,
    val date_created_from: String? = null,
    val date_created_to: String? = null,
    val search_keyword: String? = null
)
