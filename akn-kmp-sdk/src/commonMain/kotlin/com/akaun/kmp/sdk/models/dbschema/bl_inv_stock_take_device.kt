package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_inv_stock_take_device(
    var brand: String? = null,
    var code: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var guid: String? = null,
    var model: String? = null,
    var name: String? = null,
    var property_json: JsonElement? = null,
    var remarks: String? = null,
    var revision: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: String? = null,
    var vrsn: String? = null
)
