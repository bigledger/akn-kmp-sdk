package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_shipment_processing_open_queue(
    var acl_config: JsonElement? = null,
    var acl_policy: JsonElement? = null,
    var applet_guid: JsonElement? = null,
    var bal_qty: Double? = null,
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var guid: String? = null,
    var module_guid: JsonElement? = null,
    var namespace: JsonElement? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var shipment_hdr_guid: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: String? = null,
    var vrsn: JsonElement? = null
)
