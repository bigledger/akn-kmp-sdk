package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_driver_hdr(
    val acl_config: JsonObject?,
    val acl_policy: JsonObject?,
    val addresses_json: JsonObject?,
    val applet_guid: String?,
    val created_by_subject_guid: String?,
    val date_created: Instant?,
    val date_updated: Instant?,
    val driver_status: String?,
    val driver_type: String?,
    val email: String?,
    val emergency_name: String?,
    val emergency_phone_number: String?,
    val guid: String?,
    val id_no: String?,
    val join_date: Instant?,
    val license_no: String?,
    val login_subject_guid: String?,
    val module_guid: String?,
    val name: String?,
    val namespace: String?,
    val obj_type: String?,
    val phone_number: String?,
    val property_json: JsonObject?,
    val relationship: String?,
    val remarks: String?,
    val revision: String?,
    val status: String?,
    val updated_by_subject_guid: String?,
    val vrsn: String?
)
