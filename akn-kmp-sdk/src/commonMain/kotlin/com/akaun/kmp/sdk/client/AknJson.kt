package com.akaun.kmp.sdk.client

import kotlinx.serialization.json.Json

/**
 * The one JSON configuration for all akaun API traffic.
 * Tuned to behave like the GSON setup of the old Retrofit SDK (akn-kotlin-sdk).
 */
val AknJson: Json = Json {
    ignoreUnknownKeys = true   // backend adds fields freely; GSON ignored unknown ones
    coerceInputValues = true   // backend may send null for fields that have defaults (e.g. List = emptyList())
    explicitNulls = false      // request bodies omit nulls, like GSON
    encodeDefaults = true      // ...but still send non-null defaults
    isLenient = true           // backend BigDecimal numbers arrive in some String? model fields
}
