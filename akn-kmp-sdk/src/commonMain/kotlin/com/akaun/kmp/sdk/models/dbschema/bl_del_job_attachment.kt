package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_job_attachment(
    var guid: String? = null,
    var del_job_hdr_guid: String? = null,
    var file_guid: String? = null,
    var applet_guid: String? = null,
    var module_guid: String? = null,
    var file_name: String? = null,
    var import_format: String? = null,
    var file_details: String? = null,
    var upload_date: Instant? = null,
    var code: String? = null,
    var obj_type: String? = null,
    var url_key: String? = null,
    var reference_no: String? = null,
    var file_size: Double? = null,
    var process_status: String? = null,
    var error_message: String? = null,
    var created_date: Instant? = null,
    var updated_date: Instant? = null,
    var created_by_subject_guid: String? = null,
    var updated_by_subject_guid: String? = null,
    var property_json: JsonObject? = null,
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var descr: String? = null,
    var status: String? = null,
    var revision: String? = null,
    var vrsn: String? = null,
    var job_dochdr_link_guid: String? = null,
    var job_docline_link_guid: String? = null,
    var job_shipment_link_guid: String? = null
)
