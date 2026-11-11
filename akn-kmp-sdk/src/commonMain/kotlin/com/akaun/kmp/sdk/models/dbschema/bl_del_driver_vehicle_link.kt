package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_driver_vehicle_link(
    val applet_guid: JsonElement?,
    val driver_hdr_guid: String?,
    val guid: String?,
    val module_guid: JsonElement?,
    val namespace: JsonElement?,
    val revision: String?,
    val status: String?,
    val vehicle_hdr_guid: String?,
    val vrsn: JsonElement?
)
