package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_stock_take_session_ext(
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var ext_code: JsonElement? = null,
    var ext_option: JsonElement? = null,
    var ext_type: JsonElement? = null,
    var guid: String? = null,
    var guid_device: JsonElement? = null,
    var guid_hdr: String? = null,
    var guid_record: JsonElement? = null,
    var param_code: JsonElement? = null,
    var param_name: JsonElement? = null,
    var param_option_json: JsonElement? = null,
    var param_type: JsonElement? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: JsonElement? = null,
    var updated_date: String? = null,
    var value_datetime: JsonElement? = null,
    var value_file: JsonElement? = null,
    var value_json: JsonElement? = null,
    var value_numeric: JsonElement? = null,
    var value_string: JsonElement? = null,
    var vrsn: JsonElement? = null
)
