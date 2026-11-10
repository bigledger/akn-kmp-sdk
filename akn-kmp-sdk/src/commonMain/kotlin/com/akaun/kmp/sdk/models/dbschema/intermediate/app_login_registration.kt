package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class app_login_registration(
    var category: String? = null,
    var code: String? = null,
    var created_date: String? = null,
    var description: String? = null,
    var dob: String? = null,
    var email: String? = null,
    var expiry_date: String? = null,
    var guid: String? = null,
    var invite_date: String? = null,
    var invited_by: String? = null,
    var login_method: String? = null,
    var name: String? = null,
    var name_first: String? = null,
    var name_last: String? = null,
    var phone: String? = null,
    var pin_hash: String? = null,
    // Map column in the backend (JSON object), not a String as the old SDK declared
    var post_registration_json: JsonElement? = null,
    var principal_id: String? = null,
    var principal_type: String? = null,
    // Map column in the backend (JSON object), not a String as the old SDK declared
    var property_json: JsonElement? = null,
    var registration_key: String? = null,
    var registration_status: String? = null,
    var registration_type: String? = null,
    var revision: String? = null,
    var status: String? = null,
    var subject_guid: String? = null,
    var temporary_password: String? = null,
    var tenant_guid: String? = null,
    var updated_date: String? = null,
    var valid_date_end: String? = null,
    var verification_code: String? = null,
    var verification_status: String? = null,
    var vrsn: String? = null,
    var website_hdr_guid: String? = null
)
