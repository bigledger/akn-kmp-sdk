package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DeliveryInstructionsX(
    val deliveryDate: JsonElement?,
    val from: JsonElement?,
    val instructions: JsonElement?,
    val message: JsonElement?,
    val to: JsonElement?
)
