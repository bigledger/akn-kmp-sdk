package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices

import com.akaun.kmp.sdk.models.dbschema.ForgotPasswordConfirmRequest
import com.akaun.kmp.sdk.models.dbschema.ForgotPasswordInitRequest
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object IdentityPasswordPrefix {
    // The old SDK had "/identity/password", producing ".../dm//identity/password", which the server does not route
    const val PREFIX = Core2Config.PLATFORM_DOMAIN_URL_PREFIX + "identity/password"
}

class IdentityPasswordService(private val client: HttpClient) {

    suspend fun forgotPasswordConfirm(
        forgotPasswordConfirmRequest: ForgotPasswordConfirmRequest
    ): BasicApiResponseModel<String?> =
        client.post(IdentityPasswordPrefix.PREFIX + "/reset_confirm") {
            setBody(forgotPasswordConfirmRequest)
        }.body()

    suspend fun forgotPasswordInit(
        forgotPasswordInitRequest: ForgotPasswordInitRequest,
        tenantCode: String? = null
    ): BasicApiResponseModel<String?> =
        client.post(IdentityPasswordPrefix.PREFIX + "/reset_init") {
            setBody(forgotPasswordInitRequest)
            parameter("tenantCode", tenantCode)
        }.body()
}
