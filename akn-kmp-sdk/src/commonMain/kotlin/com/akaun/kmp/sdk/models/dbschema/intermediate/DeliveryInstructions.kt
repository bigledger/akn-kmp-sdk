package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryInstructions(
    val from: String?,
    val instructions: String?,
    val message: String?,
    val requestedDeliveryDate: String?,
    val to: String?
)
