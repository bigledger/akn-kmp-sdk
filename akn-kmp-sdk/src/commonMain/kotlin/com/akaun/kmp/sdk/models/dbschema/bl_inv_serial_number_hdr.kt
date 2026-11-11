package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_serial_number_hdr(
    val app_id: JsonElement? = null,
    val comp_guid: String? = null,
    val guid: String? = null,
    val item_code: JsonElement? = null,
    val item_guid: String? = null,
    val location_guid: String? = null,
    val namespace: JsonElement? = null,
    val quantity_balance: Double? = null,
    val quantity_base: JsonElement? = null,
    val revision: String? = null,
    val sn_description: JsonElement? = null,
    val sn_id: String? = null,
    val sn_option_json: JsonElement? = null,
    val sn_property_json: JsonElement? = null,
    val sn_type: String? = null,
    val status: String? = null,
    val vrsn: JsonElement? = null
)
