package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class bl_inv_mst_location(
    var address_line_1: String? = null,
    var address_line_2: String? = null,
    var address_line_3: String? = null,
    var address_line_4: String? = null,
    var address_line_5: String? = null,
    var city: String? = null,
    var client_key: String? = null,
    var client_source: String? = null,
    var client_value: String? = null,
    var code: String? = null,
    var country: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: Instant? = null,
    var descr: String? = null,
    var geoloc_latitude: Double? = null,
    var geoloc_longitude: Double? = null,
    var guid: String? = null,
    var guid_bl_fi_mst_branch: String? = null,
    var guid_bl_fi_mst_comp: String? = null,
    var location_type: String? = null,
    var modified_by_subject_guid: String? = null,
    var modified_date: Instant? = null,
    var name: String? = null,
    var postal_code: String? = null,
    var revision: String? = null,
    var state: String? = null,
    var status: String? = null,
    var vrsn: String? = null
)
