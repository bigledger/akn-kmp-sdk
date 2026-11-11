package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_trip_driver_link(
    var guid: String? = null,
    var trip_hdr_guid: String? = null,
    var driver_hdr_guid: String? = null,
    var namespace: String? = null,
    var applet_guid: String? = null,
    var module_guid: String? = null,
    var property_json: JsonElement? = null,
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var created_by_subject_guid: String? = null,
    var updated_by_subject_guid: String? = null,
    var created_date: String? = null,
    var updated_date: String? = null,
    var status: String? = null,
    var revision: String? = null,
    var vrsn: String? = null,
    var trip_driver_type: String? = null,
    var commission_value: String? = null
)
