package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices

import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.GenericDocumentDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.encodeURLPathPart

object BaseGenDocUrlPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.GEN_DOC_PREFIX
}

/** [docType] is the URL key of a document type, e.g. "internal-sales-orders" (backend GenericDocumentTypeHandler). */
class BaseGenDocTypeService(private val client: HttpClient) {

    suspend fun create(
        docType: String,
        genericDocumentModel: GenericDocumentModel
    ): BasicApiResponseModel<GenericDocumentModel> =
        client.post(BaseGenDocUrlPrefix.PREFIX + docType.encodeURLPathPart()) {
            setBody(genericDocumentModel)
        }.body()

    suspend fun getGenericDocumentByDTOSnapshot(
        docType: String,
        genericDocumentDto: GenericDocumentDto
    ): PagingResponseModel<GenericDocumentModel> =
        client.post(BaseGenDocUrlPrefix.PREFIX + docType.encodeURLPathPart() + "/custom/query/snapshot/backoffice-ep") {
            setBody(genericDocumentDto)
        }.body()

    suspend fun getGenericDocumentByGuid(
        docType: String,
        guid: String
    ): BasicApiResponseModel<GenericDocumentModel> =
        client.get(BaseGenDocUrlPrefix.PREFIX + docType.encodeURLPathPart() + "/" + guid.encodeURLPathPart()).body()

    suspend fun updateGenericDocument(
        docType: String,
        genericDocumentModel: GenericDocumentModel
    ): BasicApiResponseModel<GenericDocumentModel> =
        client.put(BaseGenDocUrlPrefix.PREFIX + docType.encodeURLPathPart()) {
            setBody(genericDocumentModel)
        }.body()
}
