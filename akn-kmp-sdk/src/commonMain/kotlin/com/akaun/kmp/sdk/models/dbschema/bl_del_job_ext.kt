package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_job_ext(
    var acl_config: JsonElement? = null,
    var acl_policy: JsonElement? = null,
    var applet_guid: JsonElement? = null,
    var created_by_subject_guid: JsonElement? = null,
    var created_date: String? = null,
    var dochdr_link_guid: JsonElement? = null,
    var docline_link_guid: JsonElement? = null,
    var ext_code: JsonElement? = null,
    var ext_option: JsonElement? = null,
    var ext_type: JsonElement? = null,
    var guid: String? = null,
    var hdr_guid: String? = null,
    var module_guid: JsonElement? = null,
    var namespace: JsonElement? = null,
    var obj_type: JsonElement? = null,
    var param_code: String? = null,
    var param_name: String? = null,
    var param_option_json: JsonElement? = null,
    var param_type: String? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: JsonElement? = null,
    var updated_date: String? = null,
    var value_datetime: JsonElement? = null,
    var value_file: JsonElement? = null,
    var value_json: JsonElement? = null,
    var value_numeric: JsonElement? = null,
    var value_string: String? = null,
    var vrsn: JsonElement? = null
)
