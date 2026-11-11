package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_fi_generic_doc_scan_line(
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var applet_guid: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: Instant? = null,
    var ean_code: String? = null,
    var generic_doc_hdr_guid: String? = null,
    var guid: String? = null,
    var guid_branch: String? = null,
    var guid_comp: String? = null,
    var item_code: String? = null,
    var item_desc: String? = null,
    var item_guid: String? = null,
    var item_name: String? = null,
    var module_guid: String? = null,
    var namespace: String? = null,
    var obj_type: String? = null,
    var property_json: JsonObject? = null,
    var qty: Double? = null,
    var qty_matched: Double? = null,
    var revision: String? = null,
    var serial_number: String? = null,
    var status: String? = null,
    var uom: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: Instant? = null,
    var vrsn: String? = null
)
