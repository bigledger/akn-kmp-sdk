package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices

import com.akaun.kmp.sdk.models.aggregates.erp.applogin.AppLoginRegistrationModel
import com.akaun.kmp.sdk.models.dbschema.RegistrationRequest
import com.akaun.kmp.sdk.models.dbschema.ResendConfirmationRequest
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object IdentityRegistrationPrefix {
    const val PREFIX = Core2Config.PLATFORM_DOMAIN_URL_PREFIX + "identity/registration"
}

class IdentityRegistrationService(private val client: HttpClient) {

    suspend fun registerWithEmail(
        registrationRequest: RegistrationRequest,
        tenantCode: String? = null,
        websiteCode: String? = null,
        inviteKey: String? = null
    ): BasicApiResponseModel<AppLoginRegistrationModel> =
        client.post(IdentityRegistrationPrefix.PREFIX + "/with-token/email/public-ep") {
            setBody(registrationRequest)
            parameter("tenantCode", tenantCode)
            parameter("websiteCode", websiteCode)
            parameter("inviteKey", inviteKey)
        }.body()

    suspend fun registerWithMobileNumber(
        registrationRequest: RegistrationRequest,
        tenantCode: String? = null,
        websiteCode: String? = null,
        inviteKey: String? = null
    ): BasicApiResponseModel<AppLoginRegistrationModel> =
        client.post(IdentityRegistrationPrefix.PREFIX + "/with-token/mobile-number/public-ep") {
            setBody(registrationRequest)
            parameter("tenantCode", tenantCode)
            parameter("websiteCode", websiteCode)
            parameter("inviteKey", inviteKey)
        }.body()

    suspend fun generateJWTToken(
        gRecaptchaResponseToken: String? = null,
    ): BasicApiResponseModel<String?> =
        client.get(IdentityRegistrationPrefix.PREFIX + "/generate-token/public-ep") {
            parameter("gRecaptchaResponseToken", gRecaptchaResponseToken)
        }.body()

    suspend fun generateJWTTokenWithoutCaptcha(): BasicApiResponseModel<String?> =
        client.get(IdentityRegistrationPrefix.PREFIX + "/generate-token-without-captcha/public-ep").body()

    suspend fun resendConfirmationEmail(
        resendConfirmationRequest: ResendConfirmationRequest,
        tenantCode: String? = null
    ): BasicApiResponseModel<AppLoginRegistrationModel> =
        client.post(IdentityRegistrationPrefix.PREFIX + "/resend_confirmation_email") {
            setBody(resendConfirmationRequest)
            parameter("tenantCode", tenantCode)
        }.body()

    suspend fun resendConfirmationMobileNumber(
        resendConfirmationRequest: ResendConfirmationRequest,
        tenantCode: String? = null
    ): BasicApiResponseModel<AppLoginRegistrationModel> =
        client.post(IdentityRegistrationPrefix.PREFIX + "/resend_confirmation_mobile_number") {
            setBody(resendConfirmationRequest)
            parameter("tenantCode", tenantCode)
        }.body()
}
