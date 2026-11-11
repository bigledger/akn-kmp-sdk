package com.akaun.kmp.sdk.models.aggregates.erp.applogin

import com.akaun.kmp.sdk.models.dbschema.app_login_principal
import com.akaun.kmp.sdk.models.dbschema.app_login_subject
import com.akaun.kmp.sdk.models.dbschema.app_login_subject_ext
import kotlinx.serialization.Serializable

@Serializable
data class AppLoginModel(
    val appLoginPrincipalList: List<app_login_principal>,
    val appLoginSubject: app_login_subject,
    val appLoginSubjectExtList: List<app_login_subject_ext>
)
