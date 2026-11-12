package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class app_login_subject(
    var account_type: String? = null,
    var category: String? = null,
    var contact_key_guid: String? = null,
    var created_date: Instant? = null,
    var description: String? = null,
    var guid: String? = null,
    var main_email: String? = null,
    var main_phone: String? = null,
    var name: String? = null,
    var name_first: String? = null,
    var name_last: String? = null,
    var property_json: JsonObject? = null,
    var rank: String? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_date: Instant? = null,
    var vrsn: String? = null
)
