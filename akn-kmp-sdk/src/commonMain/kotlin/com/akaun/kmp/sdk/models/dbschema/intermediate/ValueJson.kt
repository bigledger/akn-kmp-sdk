package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ValueJson(
    val DEFAULT_BRANCH: JsonElement?,
    var DEFAULT_LOCATION: String?
)
