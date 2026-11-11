package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_trip_hdr(
    var acl_config: JsonObject?,
    var acl_policy: JsonObject?,
    var applet_guid: String?,
    var code: String?,
    var created_by_subject_guid: String?,
    var date_created: Instant?,
    var date_updated: Instant?,
    var delivery_logic: String?,
    var delivery_region_code: String?,
    var delivery_status: String?,
    var descr: String?,
    var driver_name: String?,
    var guid: String?,
    var guid_driver_hdr: String?,
    var guid_region_hdr: String?,
    var guid_vehicle_hdr: String?,
    var label_list_guid: String?,
    var module_guid: String?,
    var name: String?,
    var namespace: String?,
    var obj_type: String?,
    var process_hdr_guid: String?,
    var process_resolution_code: String?,
    var process_resolution_guid: String?,
    var process_status_guid: String?,
    var process_status_name: String?,
    var property_json: JsonObject?,
    var revision: String?,
    var running_number_01: String?,
    var running_number_02: String?,
    var running_number_03: String?,
    var status: String?,
    var trip_end_date: Instant?,
    var trip_start_date: Instant?,
    var updated_by_subject_guid: String?,
    var vehicle_capacity: String?,
    var vehicle_reg_plate: String?,
    var vrsn: String?
)
