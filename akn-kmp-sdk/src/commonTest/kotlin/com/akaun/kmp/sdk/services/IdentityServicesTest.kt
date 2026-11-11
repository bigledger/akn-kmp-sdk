package com.akaun.kmp.sdk.services

import com.akaun.kmp.sdk.fixtures.APP_LOGIN_REGISTRATION_JSON
import com.akaun.kmp.sdk.fixtures.LOGIN_NO_APPLET_LOADING_JSON
import com.akaun.kmp.sdk.fixtures.USER_APPLET_LINKS_JSON
import com.akaun.kmp.sdk.fixtures.USER_PROFILE_JSON
import com.akaun.kmp.sdk.models.dbschema.ForgotPasswordInitRequest
import com.akaun.kmp.sdk.models.dbschema.GoogleLoginRequest
import com.akaun.kmp.sdk.models.dbschema.LoginRequest
import com.akaun.kmp.sdk.models.dbschema.RegistrationRequest
import com.akaun.kmp.sdk.models.dbschema.ResendConfirmationRequest
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices.IdentityPasswordService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices.IdentityRegistrationService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices.LoginService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.userprofileservices.UserProfileService
import com.akaun.kmp.sdk.support.MockAknClient
import com.akaun.kmp.sdk.support.MockAknClient.Companion.BASE_URL
import com.akaun.kmp.sdk.support.bodyText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class IdentityServicesTest {

    @Test
    fun loginPostsCredentialsAndTenantCode() = runTest {
        val mock = MockAknClient(responseJson = LOGIN_NO_APPLET_LOADING_JSON)

        val response = LoginService(mock.client).login(LoginRequest(password = "pw", email = "a@b.c"), tenantCode = "staging_tenant")

        val request = mock.requests.single()
        assertEquals(HttpMethod.Post, request.method)
        assertEquals("${BASE_URL}core2/platform/dm/identity/login/no-applet-loading?tenantCode=staging_tenant", request.url.toString())
        assertEquals("""{"password":"pw","email":"a@b.c"}""", request.bodyText())
        assertEquals("eyJ.fake.token", response.data!!.authToken)
    }

    @Test
    fun loginWithoutTenantCodeSendsNoQuery() = runTest {
        val mock = MockAknClient(responseJson = LOGIN_NO_APPLET_LOADING_JSON)

        LoginService(mock.client).login(LoginRequest(password = "pw", email = "a@b.c"))

        assertEquals("${BASE_URL}core2/platform/dm/identity/login/no-applet-loading", mock.requests.single().url.toString())
    }

    @Test
    fun googleLoginAndAppletLinks() = runTest {
        val google = MockAknClient(responseJson = LOGIN_NO_APPLET_LOADING_JSON)
        LoginService(google.client).loginToGoogle(GoogleLoginRequest(googleToken = "g"), inviteKey = "k1")
        assertEquals("${BASE_URL}core2/platform/dm/identity/login/google?inviteKey=k1", google.requests.single().url.toString())
        assertEquals("""{"googleToken":"g","googleAppId":""}""", google.requests.single().bodyText())

        val links = MockAknClient(responseJson = USER_APPLET_LINKS_JSON)
        val response = LoginService(links.client).getUserAppletLInks()
        assertEquals(HttpMethod.Get, links.requests.single().method)
        assertEquals("${BASE_URL}core2/platform/dm/applets/user-applet-links", links.requests.single().url.toString())
        assertEquals("stockTakeAppletV2", response.data!!.first().applet.applet_code)
    }

    @Test
    fun passwordResetUsesSingleSlashPath() = runTest {
        // Old SDK prefix was ".../dm/" + "/identity/password": the server answers "No static resource" for the // path
        val mock = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":"sent"}""")
        val service = IdentityPasswordService(mock.client)

        val result = service.forgotPasswordInit(ForgotPasswordInitRequest(email = "a@b.c"), tenantCode = "t1")
        service.forgotPasswordConfirm(com.akaun.kmp.sdk.models.dbschema.ForgotPasswordConfirmRequest(email = "a@b.c", confirmationCode = "1"))

        assertEquals("${BASE_URL}core2/platform/dm/identity/password/reset_init?tenantCode=t1", mock.requests[0].url.toString())
        assertEquals("${BASE_URL}core2/platform/dm/identity/password/reset_confirm", mock.requests[1].url.toString())
        assertEquals("sent", result.data)
    }

    @Test
    fun registrationEndpoints() = runTest {
        val mock = MockAknClient(responseJson = APP_LOGIN_REGISTRATION_JSON)
        val service = IdentityRegistrationService(mock.client)

        val registered = service.registerWithEmail(RegistrationRequest(email = "a@b.c"), tenantCode = "t1", inviteKey = "k1")
        service.registerWithMobileNumber(RegistrationRequest(mobileNumber = "+600"))
        service.resendConfirmationEmail(ResendConfirmationRequest(email = "a@b.c"))
        service.resendConfirmationMobileNumber(ResendConfirmationRequest(mobileNumber = "+600"), tenantCode = "t1")

        assertEquals(
            listOf(
                "core2/platform/dm/identity/registration/with-token/email/public-ep?tenantCode=t1&inviteKey=k1",
                "core2/platform/dm/identity/registration/with-token/mobile-number/public-ep",
                "core2/platform/dm/identity/registration/resend_confirmation_email",
                "core2/platform/dm/identity/registration/resend_confirmation_mobile_number?tenantCode=t1",
            ),
            mock.requests.map { it.url.toString().removePrefix(BASE_URL) },
        )
        assertEquals("PENDING", registered.data!!.app_login_registration!!.registration_status)
    }

    @Test
    fun registrationTokenEndpoints() = runTest {
        val mock = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":"jwt"}""")
        val service = IdentityRegistrationService(mock.client)

        assertEquals("jwt", service.generateJWTToken(gRecaptchaResponseToken = "cap").data)
        service.generateJWTTokenWithoutCaptcha()

        assertEquals("${BASE_URL}core2/platform/dm/identity/registration/generate-token/public-ep?gRecaptchaResponseToken=cap", mock.requests[0].url.toString())
        assertEquals("${BASE_URL}core2/platform/dm/identity/registration/generate-token-without-captcha/public-ep", mock.requests[1].url.toString())
    }

    @Test
    fun userProfileAndPhoto() = runTest {
        val profile = MockAknClient(responseJson = USER_PROFILE_JSON)
        assertEquals("test.user@example.com", UserProfileService(profile.client).getUserProfile().data!!.appLoginSubject.main_email)
        assertEquals("${BASE_URL}core2/platform/dm/identity/userprofile", profile.requests.single().url.toString())

        val photo = MockAknClient(responseJson = "PNGDATA")
        val bytes = UserProfileService(photo.client).getUserProfilePhoto("ext 1")
        assertEquals("${BASE_URL}core2/platform/dm/identity/userprofile/photo/ext%201", photo.requests.single().url.toString())
        assertContentEquals("PNGDATA".encodeToByteArray(), bytes)
    }
}
