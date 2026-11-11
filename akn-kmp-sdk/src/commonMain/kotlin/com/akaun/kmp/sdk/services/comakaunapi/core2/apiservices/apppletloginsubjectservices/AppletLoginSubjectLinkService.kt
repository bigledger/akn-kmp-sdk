package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.apppletloginsubjectservices

import com.akaun.kmp.sdk.models.aggregates.erp.appletloginsubject.AppletLoginSubjectLinkModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody

object Core2UrlAppletLoginSubjectLinkPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "applet-login-subject-link/"
}

class AppletLoginSubjectLinkService(private val client: HttpClient) {

    suspend fun getAppletsUsersByCriteria(
        app_login_guid: String,
        applet_hdr_guid: String,
        tenant_guid: String,
        limit: Int
    ): PagingResponseModel<AppletLoginSubjectLinkModel> =
        client.get(Core2UrlAppletLoginSubjectLinkPrefix.PREFIX + "query") {
            parameter("app_login_guid", app_login_guid)
            parameter("applet_hdr_guid", applet_hdr_guid)
            parameter("tenant_guid", tenant_guid)
            parameter("limit", limit)
        }.body()

    suspend fun updateAppletLoginSubject(
        appletLoginSubjectContainer: AppletLoginSubjectLinkModel
    ): BasicApiResponseModel<AppletLoginSubjectLinkModel> =
        client.put(Core2UrlAppletLoginSubjectLinkPrefix.PREFIX) {
            setBody(appletLoginSubjectContainer)
        }.body()
}
