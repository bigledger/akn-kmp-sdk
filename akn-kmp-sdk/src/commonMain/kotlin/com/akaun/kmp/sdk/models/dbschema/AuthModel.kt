package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    var password: String,
    var email: String? = null,
    var mobileNumber: String? = null
)

@Serializable
data class GoogleLoginRequest(
    val googleToken: String = "",
    val googleAppId: String = ""
)

@Serializable
data class RegistrationRequest(
    var password: String? = null,
    var email: String? = null,
    var mobileNumber: String? = null,
    var pinNumber: String? = null,
    var hostName: String? = null,
    var messageTemplate: String? = null,
    var validDateEnd: String? = null, // ISO-8601 (was java.time.ZonedDateTime)
    var description: String = "AKAUN",
    var domain: String = "akaun.com",
    var appLoginSubjectExtList: List<bl_applet_login_subject_link_ext> = emptyList(),
    var nameFirst: String? = null,
    var nameLast: String? = null,
    var dob: String? = null, // ISO-8601 (was java.time.ZonedDateTime)
    var appLoginType: LoginSubjectType? = null
)

@Serializable
data class ResendConfirmationRequest(
    var email: String?  = null,
    var mobileNumber: String?  = null,
    var messageTemplate: String? = null
)

@Serializable
data class ForgotPasswordConfirmRequest (
    var email : String? = null,
    var mobileNumber : String? = null,
    var confirmationCode  : String? = null,
    var newPassword  : String? = null,
)

@Serializable
data class ForgotPasswordInitRequest(
    var email: String?  = null,
    var mobileNumber: String?  = null,
    var messageTemplate: String? = null
)

@Serializable
enum class LoginSubjectType {
    PLATFORM, TENANT
}
