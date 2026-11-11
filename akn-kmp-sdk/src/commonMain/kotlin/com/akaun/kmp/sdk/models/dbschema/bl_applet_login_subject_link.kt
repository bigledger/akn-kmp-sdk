package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_applet_login_subject_link(
    var app_login_guid: String? = null,
    var applet_catalog_guid: String? = null,
    var applet_catalog_link_guid: String? = null,
    var applet_hdr_guid: String? = null,
    var code: JsonElement? = null,
    var comment_date: String? = null,
    var comments: JsonElement? = null,
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var guid: String? = null,
    var link_type: JsonElement? = null,
    var property_json: JsonElement? = null,
    var rank: String? = null,
    var rating: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var tenant_guid: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: String? = null,
    var vrsn: JsonElement? = null
)
