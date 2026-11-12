package com.akaun.kmp.sdk.models.dbschema

import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_fi_mst_comp(
    var abbreviation: String? = null,
    var address_line_1: String? = null,
    var address_line_2: String? = null,
    var address_line_3: String? = null,
    var address_line_4: String? = null,
    var address_line_5: String? = null,
    var ccy_code: String? = null,
    var chart_of_acc_guid: String? = null,
    var city: String? = null,
    var client_key: String? = null,
    var client_source: String? = null,
    var client_value: String? = null,
    var code: String? = null,
    var comp_ref1: String? = null,
    var comp_ref2: String? = null,
    var comp_ref3: String? = null,
    var comp_registration_num: String? = null,
    var comp_time_zone: String? = null,
    var country: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: Instant? = null,
    var default_tax_code_input: String? = null,
    var default_tax_code_output: String? = null,
    var descr: String? = null,
    var email: String? = null,
    var entity_hdr_guid: String? = null,
    var geoloc_latitude: String? = null,
    var geoloc_longitude: String? = null,
    var guid: String? = null,
    var log_json: JsonObject? = null,
    var modified_by_subject_guid: String? = null,
    var modified_date: Instant? = null,
    var name: String? = null,
    var phone: String? = null,
    var postal_code: String? = null,
    var property_json: JsonObject? = null,
    var revision: String? = null,
    var sst_registration_id: String? = null,
    var state: String? = null,
    var status: String? = null,
    var tax_inclusive_input: Boolean? = null,
    var tax_inclusive_output: Boolean? = null,
    var tax_registration_id: String? = null,
    var vrsn: String? = null,
    var website_url: String? = null
)
