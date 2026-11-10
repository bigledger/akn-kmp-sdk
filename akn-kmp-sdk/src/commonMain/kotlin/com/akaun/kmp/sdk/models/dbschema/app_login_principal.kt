package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class app_login_principal(
    var created_date: String? = null,
    var credential: JsonElement? = null,
    var guid: String? = null,
    var principal_id: String? = null,
    var principal_type: String? = null,
    var principal_value: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var subject_guid: String? = null,
    var updated_date: String? = null,
    var valid_date_end: String? = null,
    var valid_date_start: String? = null,
    var vrsn: JsonElement? = null
)
