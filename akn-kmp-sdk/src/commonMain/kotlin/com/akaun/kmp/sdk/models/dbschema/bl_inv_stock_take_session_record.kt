package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_inv_stock_take_session_record(
    var base_uom: String? = null,
    var created_by_subject_guid: String? = null,
    var date_created: Instant? = null,
    var date_lastupdated: Instant? = null,
    var ean_code: String? = null,
    var guid: String? = null,
    var guid_bin: String? = null,
    var guid_branch: String? = null,
    var guid_comp: String? = null,
    var guid_location: String? = null,
    var guid_session_device: String? = null,
    var guid_session_hdr: String? = null,
    var input_json: JsonObject? = null,
    var input_string: String? = null,
    var input_type: String? = null,
    var item_code: String? = null,
    var item_guid: String? = null,
    var item_name: String? = null,
    var qty: Double? = null,
    var qty_uom: Double? = null,
    var remarks: String? = null,
    var revision: String? = null,
    var serial_number: String? = null,
    var status: String? = null,
    var uom: String? = null,
    var uom_ratio: Double? = null,
    var updated_by_subject_guid: String? = null,
    var vrsn: String? = null
)
