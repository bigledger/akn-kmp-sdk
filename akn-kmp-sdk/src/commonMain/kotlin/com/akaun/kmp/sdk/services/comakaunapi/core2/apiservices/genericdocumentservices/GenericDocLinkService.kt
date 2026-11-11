package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices

import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentLinkModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

object Core2UrlGenericDocLinkServicePrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.GENERIC_DOC_PREFIX + "links"
}

class GenericDocLinkService(private val client: HttpClient) {

    suspend fun createGenericDocumentLinkBackofficeEP(
        genericDocumentLinkContainerList: List<GenericDocumentLinkModel>
    ): ApiResponseModel<GenericDocumentLinkModel> =
        client.post(Core2UrlGenericDocLinkServicePrefix.PREFIX + "/backoffice-ep") {
            setBody(genericDocumentLinkContainerList)
        }.body()
}
