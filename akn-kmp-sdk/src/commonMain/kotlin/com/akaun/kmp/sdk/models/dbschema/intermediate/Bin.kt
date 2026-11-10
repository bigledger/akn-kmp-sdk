package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Bin(
    val bin_hdr_code: String?,
    val bin_hdr_guid: JsonElement?,
    val bin_line_guid: JsonElement?,
    val container_measure: Int?,
    val container_qty: Int?,
    val qty: Int?
)
