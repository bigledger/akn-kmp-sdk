package com.akaun.kmp.sdk.models.dbschema.intermediate

import com.akaun.kmp.sdk.models.dbschema.intermediate.Batch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class BatchNo(
    val batches: List<Batch>?,
    val bins: List<JsonElement>?
)
