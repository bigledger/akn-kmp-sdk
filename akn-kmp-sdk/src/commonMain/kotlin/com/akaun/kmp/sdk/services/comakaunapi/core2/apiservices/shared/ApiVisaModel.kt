package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared

import kotlinx.serialization.Serializable

@Serializable
data class ApiVisa(
    val tenantCode: String?,
    val applet_code: String?,
    val api_domain_url: String?,
    val jwt_secret: String,
    val AccessID: String?,
    val AccessKey: String?
)
