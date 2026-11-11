package com.akaun.kmp.sdk.services

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.APPLET_LOGIN_SUBJECT_LINK_JSON
import com.akaun.kmp.sdk.fixtures.COMPANY_JSON
import com.akaun.kmp.sdk.fixtures.LOCATIONS_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.appletloginsubject.AppletLoginSubjectLinkModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.apppletloginsubjectservices.AppletLoginSubjectLinkService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.companyservices.CompanyService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.locationservices.LocationService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.support.MockAknClient
import com.akaun.kmp.sdk.support.MockAknClient.Companion.BASE_URL
import com.akaun.kmp.sdk.support.bodyText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CompanyLocationServicesTest {

    @Test
    fun companyByGuid() = runTest {
        val mock = MockAknClient(responseJson = COMPANY_JSON)

        val company = CompanyService(mock.client).getCompanyByGuid("a82c0c35-92be-43e2-9e98-06500cb8f2f1")

        val request = mock.requests.single()
        assertEquals(HttpMethod.Get, request.method)
        assertEquals("${BASE_URL}core2/tnt/dm/erp/companies/a82c0c35-92be-43e2-9e98-06500cb8f2f1", request.url.toString())
        assertEquals("IDA COMPANY", company.data!!.bl_fi_mst_comp.code)
    }

    @Test
    fun locationsAllAndByGuid() = runTest {
        val all = MockAknClient(responseJson = LOCATIONS_JSON)
        val locations = LocationService(all.client).getLocationAll()
        assertEquals("${BASE_URL}core2/tnt/dm/erp/locations", all.requests.single().url.toString())
        assertEquals("IDA LOCATION", locations.data!!.first().bl_inv_mst_location.code)

        val one = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":{"bl_inv_mst_location":{"guid":"l1","code":"IDA LOCATION"}}}""")
        val location = LocationService(one.client).getLocationByGuid("l1")
        assertEquals("${BASE_URL}core2/tnt/dm/erp/locations/l1", one.requests.single().url.toString())
        assertEquals("IDA LOCATION", location.data!!.bl_inv_mst_location.code)
    }

    @Test
    fun appletUsersQuerySendsAllCriteria() = runTest {
        val mock = MockAknClient(responseJson = APPLET_LOGIN_SUBJECT_LINK_JSON)

        val page = AppletLoginSubjectLinkService(mock.client).getAppletsUsersByCriteria(
            app_login_guid = "u1", applet_hdr_guid = "a1", tenant_guid = "t1", limit = 5,
        )

        assertEquals(
            "${BASE_URL}core2/tnt/dm/applet-login-subject-link/query?app_login_guid=u1&applet_hdr_guid=a1&tenant_guid=t1&limit=5",
            mock.requests.single().url.toString(),
        )
        assertEquals("7066aeec-cfab-40d3-afaf-f5c8a328dac5", page.data.single().bl_applet_login_subject_link.app_login_guid)
    }

    @Test
    fun updateAppletLoginSubjectPutsTheModel() = runTest {
        val model = AknJson.decodeFromString<PagingResponseModel<AppletLoginSubjectLinkModel>>(APPLET_LOGIN_SUBJECT_LINK_JSON).data.single()
        val mock = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":${AknJson.encodeToString(AppletLoginSubjectLinkModel.serializer(), model)}}""")

        val updated = AppletLoginSubjectLinkService(mock.client).updateAppletLoginSubject(model)

        val request = mock.requests.single()
        assertEquals(HttpMethod.Put, request.method)
        assertEquals("${BASE_URL}core2/tnt/dm/applet-login-subject-link/", request.url.toString()) // trailing slash is routed (checked live)
        assertTrue("\"app_login_guid\":\"7066aeec-cfab-40d3-afaf-f5c8a328dac5\"" in request.bodyText())
        assertEquals(model, updated.data)
    }
}
