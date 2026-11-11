package com.akaun.kmp.sdk.services.comakaunapi.core2.dto

import com.akaun.kmp.sdk.models.aggregates.erp.serialnumber.SerialNumberStatus
import kotlinx.serialization.Serializable

@Serializable
data class SerialNumberValidationOutputDto(
    var serialNumberList: List<SerialNumberStatus>
)
