package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class bl_inv_stock_take_session_hdr(
    var code: String? = null,
    var created_by_subject_guid: String? = null,
    var date_created: Instant? = null,
    var date_end: Instant? = null,
    var date_lastupdated: Instant? = null,
    var date_start: Instant? = null,
    var description: String? = null,
    var guid: String? = null,
    var guid_branch: String? = null,
    var guid_comp: String? = null,
    var guid_location: String? = null,
    var name: String? = null,
    var revision: String? = null,
    var status: String? = null,
    var status_adjustment: String? = null,
    var status_variance: String? = null,
    var updated_by_subject_guid: String? = null,
    var vrsn: String? = null,
    var wf_process_hdr_guid: String? = null,
    var wf_process_status_code: String? = null,
    var wf_process_status_guid: String? = null,
    var wf_resolution_code: String? = null,
    var wf_resolution_guid: String? = null
)
