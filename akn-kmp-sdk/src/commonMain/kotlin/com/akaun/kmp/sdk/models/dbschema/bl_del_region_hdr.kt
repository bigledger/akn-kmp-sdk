package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_region_hdr(
    var acl_config: JsonObject?,
    var acl_policy: JsonObject?,
    var applet_guid: String?,
    var code: String?,
    var country: String?,
    var created_by_subject_guid: String?,
    var date_created: Instant?,
    var date_updated: Instant?,
    var descr: String?,
    var geofencing_json: JsonObject?,
    var goog_polyline_complex: JsonObject?,
    var google_location_name: String?,
    var google_location_url: String?,
    var guid: String?,
    var label_list_guid: String?,
    var level_value: Int?,
    var module_guid: String?,
    var name: String?,
    var namespace: String?,
    var obj_type: String?,
    var property_json: JsonObject?,
    var region: String?,
    var region_code: String?,
    var revision: String?,
    var state: String?,
    var status: String?,
    var updated_by_subject_guid: String?,
    var usage_type: String?,
    var vrsn: String?
)
