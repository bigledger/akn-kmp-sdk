package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_region_hdr(
    var acl_config: JsonElement?,
    var acl_policy: JsonElement?,
    var applet_guid: JsonElement?,
    var code: JsonElement?,
    var country: String?,
    var created_by_subject_guid: String?,
    var date_created: String?,
    var date_updated: String?,
    var descr: String?,
    var geofencing_json: JsonElement?,
    var goog_polyline_complex: JsonElement?,
    var google_location_name: String?,
    var google_location_url: String?,
    var guid: String?,
    var label_list_guid: JsonElement?,
    var level_value: JsonElement?,
    var module_guid: JsonElement?,
    var name: JsonElement?,
    var namespace: JsonElement?,
    var obj_type: JsonElement?,
    var property_json: JsonElement?,
    var region: String?,
    var region_code: String?,
    var revision: String?,
    var state: String?,
    var status: String?,
    var updated_by_subject_guid: String?,
    var usage_type: String?,
    var vrsn: JsonElement?
)
