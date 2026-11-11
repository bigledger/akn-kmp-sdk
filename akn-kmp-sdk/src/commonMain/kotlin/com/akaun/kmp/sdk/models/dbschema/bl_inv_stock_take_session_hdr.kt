package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_stock_take_session_hdr(
    var code: String? = null,
    var created_by_subject_guid: String? = null,
    var date_created: String? = null,
    var date_end: String? = null,
    var date_lastupdated: String? = null,
    var date_start: String? = "",
    var description: JsonElement? = null,
    var guid: String? = null,
    var guid_branch: JsonElement? = null,
    var guid_comp: String? = null,
    var guid_location: String? = null,
    var name: String? = null,
    var revision: String? = null,
    var status: String? = null,
    var status_adjustment: JsonElement? = null,
    var status_variance: JsonElement? = null,
    var updated_by_subject_guid: String? = null,
    var vrsn: JsonElement? = null,
    var wf_process_hdr_guid: String? = null,
    var wf_process_status_code: JsonElement? = null,
    var wf_process_status_guid: String? = null,
    var wf_resolution_code: JsonElement? = null,
    var wf_resolution_guid: String? = null
)
