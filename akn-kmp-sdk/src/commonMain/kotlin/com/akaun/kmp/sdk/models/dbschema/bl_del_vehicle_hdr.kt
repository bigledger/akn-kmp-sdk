package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_vehicle_hdr(
    var acl_config: JsonObject?,
    var acl_policy: JsonObject?,
    var applet_guid: String?,
    var brand: String?,
    var created_by_subject_guid: String?,
    var date_created: Instant?,
    var date_updated: Instant?,
    var descr: String?,
    var engine_capacity: String?,
    var guid: String?,
    var model: String?,
    var model_year: Int?,
    var module_guid: String?,
    var name: String?,
    var namespace: String?,
    var obj_type: String?,
    var property_json: JsonObject?,
    var purchased_date: Instant?,
    var reg_plate: String?,
    var revision: String?,
    var status: String?,
    var updated_by_subject_guid: String?,
    var vehicle_capacity: String?,
    var vehicle_status: String?,
    var vrsn: String?
)
