package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable

@Serializable
data class bl_del_driver_vehicle_link(
    val applet_guid: String?,
    val driver_hdr_guid: String?,
    val guid: String?,
    val module_guid: String?,
    val namespace: String?,
    val revision: String?,
    val status: String?,
    val vehicle_hdr_guid: String?,
    val vrsn: String?
)
