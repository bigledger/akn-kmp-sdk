package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable

@Serializable
data class LinePropertyJson(
    val deliveryInstructions: DeliveryInstructions?,
    val delivery_instructions: DeliveryInstructionsX?
)
