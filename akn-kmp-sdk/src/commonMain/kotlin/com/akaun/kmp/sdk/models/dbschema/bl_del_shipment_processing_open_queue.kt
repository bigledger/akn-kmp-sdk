package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_shipment_processing_open_queue(
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var applet_guid: String? = null,
    var bal_qty: Double? = null,
    var created_by_subject_guid: String? = null,
    var created_date: Instant? = null,
    var guid: String? = null,
    var module_guid: String? = null,
    var namespace: String? = null,
    var property_json: JsonObject? = null,
    var revision: String? = null,
    var shipment_hdr_guid: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: Instant? = null,
    var vrsn: String? = null
)
