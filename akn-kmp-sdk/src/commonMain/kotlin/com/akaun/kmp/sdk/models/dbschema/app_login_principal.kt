package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class app_login_principal(
    var created_date: Instant? = null,
    var credential: String? = null,
    var guid: String? = null,
    var principal_id: String? = null,
    var principal_type: String? = null,
    var principal_value: String? = null,
    var revision: String? = null,
    var status: String? = null,
    var subject_guid: String? = null,
    var updated_date: Instant? = null,
    var valid_date_end: Instant? = null,
    var valid_date_start: Instant? = null,
    var vrsn: String? = null
)
