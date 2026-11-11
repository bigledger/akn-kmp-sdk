package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_mst_location_ext(
    var created_date: String? = null,
    var ext_code: JsonElement? = null,
    var ext_option: JsonElement? = null,
    var ext_type: String? = null,
    var guid: String? = null,
    var guid_hdr: String? = null,
    var guid_line: JsonElement? = null,
    var param_code: String? = null,
    var param_name: String? = null,
    var param_option_json: JsonElement? = null,
    var param_type: String? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_date: String? = null,
    var value_datetime: JsonElement? = null,
    var value_file: JsonElement? = null,
    var value_json: JsonElement? = null,
    var value_numeric: JsonElement? = null,
    var value_string: String? = null,
    var vrsn: JsonElement? = null
)
