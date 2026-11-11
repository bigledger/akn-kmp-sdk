package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class app_login_subject(
    var account_type: JsonElement? = null,
    var category: String? = null,
    var contact_key_guid: JsonElement? = null,
    var created_date: String? = null,
    var description: String? = null,
    var guid: String? = null,
    var main_email: String? = null,
    var main_phone: String? = null,
    var name: String? = null,
    var name_first: String? = null,
    var name_last: String? = null,
    var property_json: JsonElement? = null,
    var rank: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_date: String? = null,
    var vrsn: JsonElement? = null
)
