package com.akaun.kmp.sdk.services.comakaunapi.core2.dto

import kotlinx.serialization.Serializable

@Serializable
data class JobsDto(
    val jobs_guids : Set<String> = emptySet(),
    val tripHdrGuid : String? = null

)
