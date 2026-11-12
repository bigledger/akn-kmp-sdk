package com.akaun.kmp.sdk.models.dbschema

import com.akaun.kmp.sdk.models.dbschema.intermediate.LogJson
import com.akaun.kmp.sdk.models.dbschema.intermediate.ParameterJson
import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_inv_mst_item_line(
    var amount: Double? = null,
    var ccy_code: String? = null,
    var ccy_xrate: Double? = null,
    var client_key: String? = null,
    var client_source: String? = null,
    var client_value: String? = null,
    var code: String? = null,
    var uom: String? = null,
    var created_date: Instant? = null,
    var descr: String? = null,
    var dim_height: Double? = null,
    var dim_length: Double? = null,
    var dim_weight: Double? = null,
    var dim_width: Double? = null,
    var guid: String? = null,
    var hdr_guid: String? = null,
    var item_hdr_guid: String? = null,
    var item_line_guid: String? = null,
    var log_json: JsonObject? = null,
    var mkt_category: String? = null,
    var mkt_guid: String? = null,
    var mkt_item_id: String? = null,
    var mkt_item_status: String? = null,
    var name: String? = null,
    var parameter_attributes: String? = null,
    var parameter_code: String? = null,
    var parameter_json: JsonObject? = null,
    var parameter_name: String? = null,
    var parameter_type: String? = null,
    var parameter_value: String? = null,
    var property_json: JsonObject? = null,
    var quantity: Double? = null,
    var revision: String? = null,
    var scan_code: String? = null,
    var status: String? = null,
    var sub_item_type: String? = null,
    var txn_type: String? = null
)
