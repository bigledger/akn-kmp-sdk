package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.userprofileservices

import com.akaun.kmp.sdk.models.aggregates.erp.applogin.AppLoginModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLPathPart

object Core2UrlUserProfilePrefix {
    const val PREFIX = Core2Config.PLATFORM_DOMAIN_URL_PREFIX + "identity/userprofile"
}

class UserProfileService(private val client: HttpClient) {

    suspend fun getUserProfile(): BasicApiResponseModel<AppLoginModel> =
        client.get(Core2UrlUserProfilePrefix.PREFIX).body()

    /** Raw image bytes (was okhttp3.ResponseBody in the old SDK). */
    suspend fun getUserProfilePhoto(extGuid: String): ByteArray =
        client.get(Core2UrlUserProfilePrefix.PREFIX + "/photo/" + extGuid.encodeURLPathPart()).body()
}
