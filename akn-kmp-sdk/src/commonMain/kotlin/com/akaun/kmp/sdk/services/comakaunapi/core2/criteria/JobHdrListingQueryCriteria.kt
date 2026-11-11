package com.akaun.kmp.sdk.services.comakaunapi.core2.criteria

import kotlinx.serialization.Serializable

@Serializable
data class JobHdrListingQueryCriteria(
    var trip_guids: Set<String>? = null,
    var job_hdr_guids: Set<String>? = null,
    var process_hdr_guids: Set<String>? = null,
    var process_status_guids: Set<String>? = null,
    var process_resolution_guids: Set<String>? = null,
    var sender_entity_hdr_guids: Set<String>? = null,
    var recipient_entity_hdr_guids: Set<String>? = null,
    var created_by_subject_guids: Set<String>? = null,
    var updated_by_subject_guids: Set<String>? = null,
    var region_hdr_guids: Set<String>? = null,
    var label_list_guids: Set<String>? = null,
    var from_branch_guids: Set<String>? = null,
    var to_branch_guids: Set<String>? = null,
    var from_location_guids: Set<String>? = null,
    var to_location_guids: Set<String>? = null,
    var from_logistic_hub_guids: Set<String>? = null,
    var to_logistic_hub_guids: Set<String>? = null,
    var delivery_status: Set<String>? = null,
    var job_hdr_from_start_date: String? = null,
    var job_hdr_to_start_date: String? = null,
    var job_hdr_from_end_date: String? = null,
    var job_hdr_to_end_date: String? = null,
    var date_created_from: String? = null,
    var date_created_to: String? = null,
    var search_keyword: String? = null
)
