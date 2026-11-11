package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_del_driver_hdr(
    val acl_config: JsonElement?,
    val acl_policy: JsonElement?,
    val addresses_json: JsonElement?,
    val applet_guid: JsonElement?,
    val created_by_subject_guid: String?,
    val date_created: String?,
    val date_updated: String?,
    val driver_status: String?,
    val driver_type: JsonElement?,
    val email: String?,
    val emergency_name: String?,
    val emergency_phone_number: String?,
    val guid: String?,
    val id_no: String?,
    val join_date: String?,
    val license_no: String?,
    val login_subject_guid: String?,
    val module_guid: JsonElement?,
    val name: String?,
    val namespace: JsonElement?,
    val obj_type: JsonElement?,
    val phone_number: String?,
    val property_json: JsonElement?,
    val relationship: String?,
    val remarks: JsonElement?,
    val revision: String?,
    val status: String?,
    val updated_by_subject_guid: String?,
    val vrsn: JsonElement?
)
