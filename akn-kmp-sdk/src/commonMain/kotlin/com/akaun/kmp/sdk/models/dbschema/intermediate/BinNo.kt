package com.akaun.kmp.sdk.models.dbschema.intermediate

import com.akaun.kmp.sdk.models.dbschema.intermediate.Bin
import kotlinx.serialization.Serializable

@Serializable
data class BinNo(
    val bins: List<Bin>?
)
