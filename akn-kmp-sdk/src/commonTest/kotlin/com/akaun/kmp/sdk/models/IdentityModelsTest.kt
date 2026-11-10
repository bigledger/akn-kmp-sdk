package com.akaun.kmp.sdk.models

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.APP_LOGIN_REGISTRATION_JSON
import com.akaun.kmp.sdk.fixtures.LOGIN_NO_APPLET_LOADING_JSON
import com.akaun.kmp.sdk.fixtures.LOGIN_PASSWORD_EXPIRED_JSON
import com.akaun.kmp.sdk.fixtures.USER_APPLET_LINKS_JSON
import com.akaun.kmp.sdk.fixtures.USER_PROFILE_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.UserAppletLinkResponse
import com.akaun.kmp.sdk.models.aggregates.erp.applogin.AppLoginModel
import com.akaun.kmp.sdk.models.aggregates.erp.applogin.AppLoginRegistrationModel
import com.akaun.kmp.sdk.models.dbschema.LoginRequest
import com.akaun.kmp.sdk.models.dbschema.LoginSubjectType
import com.akaun.kmp.sdk.models.dbschema.RegistrationRequest
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.LoginResponse
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class IdentityModelsTest {

    @Test
    fun userAppletLinksDecodeFromRealResponse() {
        val response = AknJson.decodeFromString<ApiResponseModel<UserAppletLinkResponse>>(USER_APPLET_LINKS_JSON)

        assertEquals("OK_RESPONSE", response.code)
        val stockTake = response.data!!.first()
        assertEquals("stockTakeAppletV2", stockTake.applet.applet_code)
        assertEquals("staging_tenant", stockTake.tenant.tenant_code)
        assertEquals("Developer_Catalog", stockTake.catalog.catalog_code)
        assertEquals(false, stockTake.isStore)
    }

    @Test
    fun userProfileDecodesFromRealResponse() {
        val profile = AknJson.decodeFromString<BasicApiResponseModel<AppLoginModel>>(USER_PROFILE_JSON).data!!

        assertEquals("7066aeec-cfab-40d3-afaf-f5c8a328dac5", profile.appLoginSubject.guid)
        assertEquals("test.user@example.com", profile.appLoginSubject.main_email)
        // JSON object in an untyped (Any?) column arrives as JsonElement
        assertEquals("redacted", profile.appLoginSubject.property_json!!.jsonObject["loginContactNumber"]!!.jsonPrimitive.content)
        assertNull(profile.appLoginSubject.rank)
        assertEquals("EMAIL_USERNAME", profile.appLoginPrincipalList.single().principal_type)
        assertEquals(1.0, profile.appLoginSubjectExtList.single().value_numeric)
    }

    @Test
    fun loginResponseDecodes() {
        val login = AknJson.decodeFromString<BasicApiResponseModel<LoginResponse>>(LOGIN_NO_APPLET_LOADING_JSON).data!!

        assertEquals("eyJ.fake.token", login.authToken)
        assertEquals(emptyList(), login.appletTenantTokenList)
        assertEquals("", login.mobileNumber)
    }

    @Test
    fun loginRejectionDecodesWithNullData() {
        val response = AknJson.decodeFromString<BasicApiResponseModel<LoginResponse>>(LOGIN_PASSWORD_EXPIRED_JSON)

        assertEquals("CLIENT_AUTH_USER_NOT_AUTHORIZED", response.code)
        assertNull(response.data)
    }

    @Test
    fun appLoginRegistrationAcceptsJsonObjectColumns() {
        val registration = AknJson.decodeFromString<BasicApiResponseModel<AppLoginRegistrationModel>>(APP_LOGIN_REGISTRATION_JSON)
            .data!!.app_login_registration!!

        assertEquals("PENDING", registration.registration_status)
        assertEquals("mobile", registration.property_json!!.jsonObject["source"]!!.jsonPrimitive.content)
    }

    @Test
    fun loginRequestOmitsUnusedFields() {
        val json = AknJson.encodeToString(LoginRequest.serializer(), LoginRequest(password = "pw", email = "test.user@example.com"))
        assertEquals("""{"password":"pw","email":"test.user@example.com"}""", json)
    }

    @Test
    fun registrationRequestSendsDefaultsAndEnumByName() {
        val json = AknJson.encodeToString(
            RegistrationRequest.serializer(),
            RegistrationRequest(email = "test.user@example.com", appLoginType = LoginSubjectType.PLATFORM),
        )
        assertEquals(
            """{"email":"test.user@example.com","description":"AKAUN","domain":"akaun.com","appLoginSubjectExtList":[],"appLoginType":"PLATFORM"}""",
            json,
        )
    }

    @Test
    fun userAppletLinkReadsSnakeCaseNotificationCount() {
        // Backend sends notification_count; the old model's notificationCount never matched it (always 0 under GSON)
        val json = """
            {"guid":"g","user_applet_rank":"ADMIN","applet_catalog_link_guid":"c","user_applet_index":0,"status":"ACTIVE",
             "applet":{"guid":"a","applet_mf_html_tag":"","applet_code":"x","applet_shortcode":"","applet_icon_guid":"",
                       "applet_name":"","applet_description":"","es_module_url":"","routerlink":"","store_guid":"","status":"ACTIVE"},
             "catalog":{"guid":"c","catalog_code":"","catalog_name":"","status":"ACTIVE"},
             "tenant":{"guid":"t","tenant_code":"","tenant_name":"","status":"ACTIVE"},
             "store":{"guid":"s","code":"","name":""},
             "isStore":false,"notification_count":5}
        """
        assertEquals(5, AknJson.decodeFromString<UserAppletLinkResponse>(json).notificationCount)
    }
}
