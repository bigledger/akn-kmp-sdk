package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable

@Serializable
data class Batch(
    val batch_no: String?,
    val expiry_date: String?,
    val issue_date: String?,
    val qty: Int?
)
