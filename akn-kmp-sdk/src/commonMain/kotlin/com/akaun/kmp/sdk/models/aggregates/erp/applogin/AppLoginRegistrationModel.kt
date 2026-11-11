package com.akaun.kmp.sdk.models.aggregates.erp.applogin

import com.akaun.kmp.sdk.models.dbschema.intermediate.app_login_registration
import kotlinx.serialization.Serializable

@Serializable
data class AppLoginRegistrationModel(
    var app_login_registration : app_login_registration?
)
