package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices

import com.akaun.kmp.sdk.models.aggregates.erp.UserAppletLinkResponse
import com.akaun.kmp.sdk.models.dbschema.GoogleLoginRequest
import com.akaun.kmp.sdk.models.dbschema.LoginRequest
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object LoginServicePrefix {
    const val PREFIX = Core2Config.PLATFORM_DOMAIN_URL_PREFIX + "identity/login"
}

// The old SDK's *WithCall variants (Retrofit Call<>) are dropped; use the suspend functions.
class LoginService(private val client: HttpClient) {

    suspend fun login(
        loginRequest: LoginRequest,
        tenantCode: String? = null
    ): BasicApiResponseModel<LoginResponse> =
        client.post(LoginServicePrefix.PREFIX + "/no-applet-loading") {
            setBody(loginRequest)
            parameter("tenantCode", tenantCode)
        }.body()

    suspend fun loginToGoogle(
        loginRequest: GoogleLoginRequest,
        inviteKey: String? = null
    ): BasicApiResponseModel<LoginResponse> =
        client.post(LoginServicePrefix.PREFIX + "/google") {
            setBody(loginRequest)
            parameter("inviteKey", inviteKey)
        }.body()

    suspend fun getUserAppletLInks(): ApiResponseModel<UserAppletLinkResponse> =
        client.get(Core2Config.PLATFORM_DOMAIN_URL_PREFIX + "applets/user-applet-links").body()
}
