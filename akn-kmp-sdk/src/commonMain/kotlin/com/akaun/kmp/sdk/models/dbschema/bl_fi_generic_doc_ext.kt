package com.akaun.kmp.sdk.models.dbschema

import com.akaun.kmp.sdk.models.dbschema.intermediate.ParamOptionJson
import com.akaun.kmp.sdk.models.dbschema.intermediate.PropertyJson
import com.akaun.kmp.sdk.models.dbschema.intermediate.ValueJson
import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_fi_generic_doc_ext(
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var applet_guid: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: Instant? = null,
    var ext_code: String? = null,
    var ext_option: String? = null,
    var ext_type: String? = null,
    var guid: String? = null,
    var guid_doc_hdr: String? = null,
    var guid_doc_line: String? = null,
    var module_guid: String? = null,
    var namespace: String? = null,
    var param_code: String? = null,
    var param_name: String? = null,
    var param_option_json: JsonObject? = null,
    var param_type: String? = null,
    var property_json: JsonObject? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: Instant? = null,
    var value_datetime: Instant? = null,
    var value_file: String? = null,
    var value_json: JsonObject? = null,
    var value_numeric: Double? = null,
    var value_string: String? = null,
    var vrsn: String? = null
)
