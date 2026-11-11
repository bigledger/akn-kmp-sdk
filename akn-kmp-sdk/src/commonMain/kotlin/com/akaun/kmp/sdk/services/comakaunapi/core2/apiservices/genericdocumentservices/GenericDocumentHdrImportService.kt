package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices

import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentHdrLineLinkQueueListModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodeURLPathPart

object Core2UrlGenericDocumentHdrImportServicePrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.GEN_DOC_PREFIX + "hdr-imports"
}

class GenericDocumentHdrImportService(private val client: HttpClient) {

    suspend fun getGenericDocumentHdrLineLinkOpenQueueByCriteria(
        docType: String?,
        line_open_queue_server_doc_type_1: String?,
        line_open_queue_server_doc_type_2: String?,
        offset: Int?,
        limit: Int?,
        guids: Set<String>?
    ): PagingResponseModel<GenericDocumentHdrLineLinkQueueListModel> =
        // Nullable only for source compatibility: Retrofit also threw on a null @Path value
        client.get(Core2UrlGenericDocumentHdrImportServicePrefix.PREFIX + "/" + requireNotNull(docType) { "docType must not be null" }.encodeURLPathPart() + "/query") {
            parameter("line_open_queue_server_doc_type_1", line_open_queue_server_doc_type_1)
            parameter("line_open_queue_server_doc_type_2", line_open_queue_server_doc_type_2)
            parameter("offset", offset)
            parameter("limit", limit)
            // Repeated keys like Retrofit (guids=a&guids=b); parameter() would send "[a, b]"
            guids?.let { url.parameters.appendAll("guids", it) }
        }.body()
}
