package com.akaun.kmp.sdk.models.dbschema

import com.akaun.kmp.sdk.models.dbschema.intermediate.ParamOptionJson
import com.akaun.kmp.sdk.models.dbschema.intermediate.PropertyJson
import com.akaun.kmp.sdk.models.dbschema.intermediate.ValueJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_fi_generic_doc_ext(
    var acl_config: JsonElement? = null,
    var acl_policy: JsonElement? = null,
    var applet_guid: JsonElement? = null,
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var ext_code: JsonElement? = null,
    var ext_option: JsonElement? = null,
    var ext_type: JsonElement? = null,
    var guid: String? = null,
    var guid_doc_hdr: String? = null,
    var guid_doc_line: JsonElement? = null,
    var module_guid: JsonElement? = null,
    var namespace: JsonElement? = null,
    var param_code: String? = null,
    var param_name: String? = null,
    var param_option_json: JsonElement? = null,
    var param_type: String? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: String? = null,
    var value_datetime: JsonElement? = null,
    var value_file: JsonElement? = null,
    var value_json: JsonElement? = null,
    var value_numeric: JsonElement? = null,
    var value_string: String? = null,
    var vrsn: JsonElement? = null
)
