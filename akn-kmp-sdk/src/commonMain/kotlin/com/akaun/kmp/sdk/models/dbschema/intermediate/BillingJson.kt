package com.akaun.kmp.sdk.models.dbschema.intermediate

import com.akaun.kmp.sdk.models.dbschema.intermediate.BillingInfo
import kotlinx.serialization.Serializable

@Serializable
data class BillingJson(
    val billingInfo: BillingInfo?
)
