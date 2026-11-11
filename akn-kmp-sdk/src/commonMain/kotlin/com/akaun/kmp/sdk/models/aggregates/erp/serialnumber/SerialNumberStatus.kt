package com.akaun.kmp.sdk.models.aggregates.erp.serialnumber

import kotlinx.serialization.Serializable

@Serializable
data class SerialNumberStatus(
    var sn_id: String? = null,
    var status: String? = null,
    var remarks: String? = null
)
