package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_fi_mst_comp_ext(
    var comp_hdr_guid: String? = null,
    var comp_line_guid: String? = null,
    var created_date: String? = null,
    var ext_code: String? = null,
    var ext_option: String? = null,
    var ext_type: String? = null,
    var guid: String? = null,
    var param_code: String? = null,
    var param_name: String? = null,
    var param_option_json: JsonElement? = null,
    var param_type: String? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_date: String? = null,
    var value_datetime: String? = null,
    var value_file: String? = null,
    var value_json: JsonElement? = null,
    var value_numeric: String? = null,
    var value_string: String? = null,
    var vrsn: String? = null
)
