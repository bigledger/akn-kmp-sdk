package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_trip_hdr(
    var acl_config: JsonElement?,
    var acl_policy: JsonElement?,
    var applet_guid: JsonElement?,
    var code: String?,
    var created_by_subject_guid: String?,
    var date_created: String?,
    var date_updated: String?,
    var delivery_logic: JsonElement?,
    var delivery_region_code: String?,
    var delivery_status: String?,
    var descr: JsonElement?,
    var driver_name: String?,
    var guid: String?,
    var guid_driver_hdr: String?,
    var guid_region_hdr: String?,
    var guid_vehicle_hdr: String?,
    var label_list_guid: JsonElement?,
    var module_guid: JsonElement?,
    var name: String?,
    var namespace: JsonElement?,
    var obj_type: JsonElement?,
    var process_hdr_guid: JsonElement?,
    var process_resolution_code: JsonElement?,
    var process_resolution_guid: JsonElement?,
    var process_status_guid: JsonElement?,
    var process_status_name: JsonElement?,
    var property_json: JsonElement?,
    var revision: String?,
    var running_number_01: String?,
    var running_number_02: String?,
    var running_number_03: String?,
    var status: String?,
    var trip_end_date: String?,
    var trip_start_date: String?,
    var updated_by_subject_guid: String?,
    var vehicle_capacity: JsonElement?,
    var vehicle_reg_plate: String?,
    var vrsn: JsonElement?
)
