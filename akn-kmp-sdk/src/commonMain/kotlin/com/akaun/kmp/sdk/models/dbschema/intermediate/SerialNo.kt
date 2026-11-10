package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable

@Serializable
data class SerialNo(
    // Backend sends serial_no {} for lines without serials; without a default that fails the whole document
    val serialNumbers: List<String> = emptyList()
)
