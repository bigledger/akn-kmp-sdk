package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_inv_serial_number_hdr(
    val app_id: String? = null,
    val comp_guid: String? = null,
    val guid: String? = null,
    val item_code: String? = null,
    val item_guid: String? = null,
    val location_guid: String? = null,
    val namespace: String? = null,
    val quantity_balance: Double? = null,
    val quantity_base: Double? = null,
    val revision: String? = null,
    val sn_description: String? = null,
    val sn_id: String? = null,
    val sn_option_json: JsonObject? = null,
    val sn_property_json: JsonObject? = null,
    val sn_type: String? = null,
    val status: String? = null,
    val vrsn: String? = null
)
