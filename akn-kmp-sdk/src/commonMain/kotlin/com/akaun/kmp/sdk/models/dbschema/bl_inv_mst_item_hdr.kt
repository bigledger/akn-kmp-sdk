package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_mst_item_hdr(
    var ccy_code: String? = null,
    var ccy_xrate: String? = null,
    var client_key: String? = null,
    var client_source: String? = null,
    var client_value: String? = null,
    var code: String? = null,
    var code_alias_json: JsonElement? = null,
    var created_date: String? = null,
    var descr: String? = null,
    var dim_height: Double? = null,
    var dim_length: Double? = null,
    var dim_weight: Double? = null,
    var dim_width: Double? = null,
    var emp_status: String? = null,
    var guid: String? = null,
    var guid_fi_mst_item: String? = null,
    var name: String? = null,
    var pkg_no_of_parts: JsonElement? = null,
    var pkg_type: String? = null,
    var price_json: JsonElement? = null,
    var revision: String? = null,
    var scan_code: String? = null,
    var status: String? = null,
    var sub_item_type: String? = null,
    var txn_type: String? = null,
    var uom: String? = null,
    var updated_date: String? = null,
    var vrsn: String? = null
)
