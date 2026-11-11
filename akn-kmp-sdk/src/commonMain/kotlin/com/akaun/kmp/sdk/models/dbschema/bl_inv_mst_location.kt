package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_mst_location(
    var address_line_1: String? = null,
    var address_line_2: JsonElement? = null,
    var address_line_3: JsonElement? = null,
    var address_line_4: JsonElement? = null,
    var address_line_5: JsonElement? = null,
    var city: JsonElement? = null,
    var client_key: JsonElement? = null,
    var client_source: JsonElement? = null,
    var client_value: JsonElement? = null,
    var code: String? = null,
    var country: JsonElement? = null,
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var descr: String? = null,
    var geoloc_latitude: JsonElement? = null,
    var geoloc_longitude: JsonElement? = null,
    var guid: String? = null,
    var guid_bl_fi_mst_branch: String? = null,
    var guid_bl_fi_mst_comp: String? = null,
    var location_type: JsonElement? = null,
    var modified_by_subject_guid: String? = null,
    var modified_date: String? = null,
    var name: String? = null,
    var postal_code: JsonElement? = null,
    var revision: String? = null,
    var state: String? = null,
    var status: String? = null,
    var vrsn: String? = null
)
