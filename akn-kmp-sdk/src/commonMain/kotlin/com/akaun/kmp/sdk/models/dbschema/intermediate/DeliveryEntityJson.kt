package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryEntityJson(
    val shippingInfo: ShippingInfo?
)
