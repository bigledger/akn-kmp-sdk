package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_vehicle_hdr(
    var acl_config: JsonElement?,
    var acl_policy: JsonElement?,
    var applet_guid: JsonElement?,
    var brand: String?,
    var created_by_subject_guid: String?,
    var date_created: String?,
    var date_updated: String?,
    var descr: JsonElement?,
    var engine_capacity: String?,
    var guid: String?,
    var model: String?,
    var model_year: JsonElement?,
    var module_guid: JsonElement?,
    var name: JsonElement?,
    var namespace: JsonElement?,
    var obj_type: JsonElement?,
    var property_json: JsonElement?,
    var purchased_date: String?,
    var reg_plate: String?,
    var revision: String?,
    var status: String?,
    var updated_by_subject_guid: String?,
    var vehicle_capacity: String?,
    var vehicle_status: String?,
    var vrsn: JsonElement?
)
