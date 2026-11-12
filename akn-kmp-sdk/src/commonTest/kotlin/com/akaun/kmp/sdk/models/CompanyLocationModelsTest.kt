package com.akaun.kmp.sdk.models

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.APPLET_LOGIN_SUBJECT_LINK_JSON
import com.akaun.kmp.sdk.fixtures.COMPANY_JSON
import com.akaun.kmp.sdk.fixtures.LOCATIONS_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.appletloginsubject.AppletLoginSubjectLinkModel
import com.akaun.kmp.sdk.models.aggregates.erp.company.CompanyModel
import com.akaun.kmp.sdk.models.aggregates.erp.location.LocationModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Instant

class CompanyLocationModelsTest {

    @Test
    fun locationsDecodeIncludingFieldsTheOldSdkNeverFilled() {
        val locations = AknJson.decodeFromString<ApiResponseModel<LocationModel>>(LOCATIONS_JSON).data!!

        val location = locations.first().bl_inv_mst_location
        assertEquals("IDA LOCATION", location.code)
        // Old SDK declared these as guidBlFiMstComp / addressLine1, so they were always null
        assertEquals("a82c0c35-92be-43e2-9e98-06500cb8f2f1", location.guid_bl_fi_mst_comp)
        assertEquals("redacted", location.address_line_1)
        assertEquals(2, locations.size)
    }

    @Test
    fun companyDecodesWithExtRows() {
        val company = AknJson.decodeFromString<BasicApiResponseModel<CompanyModel>>(COMPANY_JSON).data!!

        assertEquals("IDA COMPANY", company.bl_fi_mst_comp.code)
        assertEquals("MYR", company.bl_fi_mst_comp.ccy_code)
        assertEquals(
            listOf("COMPANY_ATTACHMENT", "BUSINESS_REGISTRATION_NUMBER", "CREATED_BY"),
            company.bl_fi_mst_comp_ext.map { it.param_code },
        )
        val createdBy = company.bl_fi_mst_comp_ext.single { it.param_code == "CREATED_BY" }
        assertEquals("7c6c67cf-8dfc-40a2-a964-5351dbb214c9", createdBy.value_json!!["guid"]!!.jsonPrimitive.content)
        // ZonedDateTime -> Instant, Map -> JsonObject (issue #12)
        assertEquals(Instant.parse("2024-08-09T00:46:51.657Z"), company.bl_fi_mst_comp.created_date)
        assertEquals(true, company.bl_fi_mst_comp.property_json!!["tinValidated"]!!.jsonPrimitive.boolean)
    }

    @Test
    fun appletLoginSubjectLinkPageDecodes() {
        val page = AknJson.decodeFromString<PagingResponseModel<AppletLoginSubjectLinkModel>>(APPLET_LOGIN_SUBJECT_LINK_JSON)

        val link = page.data.single().bl_applet_login_subject_link
        assertEquals("7066aeec-cfab-40d3-afaf-f5c8a328dac5", link.app_login_guid)
        assertEquals("7ea6aefe-793a-40fd-8094-2dc2943cca5b", link.applet_hdr_guid)
        assertEquals(5, page.limit)
    }

    @Test
    fun appletLoginSubjectLinkSurvivesRoundTripAsRequestBody() {
        // Same model is the PUT applet-login-subject-link/ request body
        val original = AknJson.decodeFromString<PagingResponseModel<AppletLoginSubjectLinkModel>>(APPLET_LOGIN_SUBJECT_LINK_JSON).data.single()

        val body = AknJson.encodeToString(AppletLoginSubjectLinkModel.serializer(), original)

        assertTrue("\"app_login_guid\":\"7066aeec-cfab-40d3-afaf-f5c8a328dac5\"" in body)
        assertEquals(original, AknJson.decodeFromString<AppletLoginSubjectLinkModel>(body))
    }
}
