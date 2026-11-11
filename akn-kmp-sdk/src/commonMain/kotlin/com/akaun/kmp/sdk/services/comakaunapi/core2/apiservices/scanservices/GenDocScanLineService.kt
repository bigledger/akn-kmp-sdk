package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.scanservices

import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentScanLineItemQtyContainer
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentScanLineModel
import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.InventoryItemModel
import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.SerialNumberModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices.InvItemService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.serialnumberservices.SerialNumberService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.utils.wrapper.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.encodeURLPathPart

object GenericDocScanLineUrlPrefix {
    const val Prefix = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.GENERIC_DOC_PREFIX + "scan-lines"
}


class GenDocScanLineService(private val client: HttpClient) {

    /* GenericDocumentScanLineController */

    suspend fun createGenDocScanLine(
        genericDocumentScanLineModel: GenericDocumentScanLineModel
    ): BasicApiResponseModel<GenericDocumentScanLineModel> =
        client.post(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep") { setBody(genericDocumentScanLineModel) }.body()

    suspend fun createMultiGenDocScanLine(
        genericDocumentScanLineModels: List<GenericDocumentScanLineModel>
    ): ApiResponseModel<GenericDocumentScanLineModel> =
        client.post(GenericDocScanLineUrlPrefix.Prefix + "/multi/backoffice-ep") { setBody(genericDocumentScanLineModels) }.body()

    suspend fun updateGenDocScanLine(
        genericDocumentScanLineModel: GenericDocumentScanLineModel
    ): BasicApiResponseModel<GenericDocumentScanLineModel> =
        client.put(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep") { setBody(genericDocumentScanLineModel) }.body()

    suspend fun getAllGenDocScanLine(
        item_code: String = "",
        location_guid: String = ""
    ): ApiResponseModel<GenericDocumentScanLineModel> =
        client.get(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep") {
            parameter("item_code", item_code)
            parameter("location_guid", location_guid)
        }.body()

    suspend fun getByGuidGenDocScanLine(guid: String): BasicApiResponseModel<GenericDocumentScanLineModel> =
        client.get(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep/" + guid.encodeURLPathPart()).body()

    suspend fun getQtyDiscrepancyByHdrGuid(genDocHdrGuid: String): ApiResponseModel<GenericDocumentScanLineItemQtyContainer> =
        client.get(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep/qty-discrepancy/" + genDocHdrGuid.encodeURLPathPart()).body()

    suspend fun getByCriteriaGenDocScanLine(
        generic_doc_hdr_guid: String? = null,
        guid_comp: String? = null,
        location_guid: String? = null,
        item_guid: String? = null,
        item_code: String? = null,
        ean_code: String? = null,
        serial_number: String? = null,
    ): PagingResponseModel<GenericDocumentScanLineModel> =
        client.get(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep/query") {
            parameter("generic_doc_hdr_guid", generic_doc_hdr_guid)
            parameter("guid_comp", guid_comp)
            parameter("guid_branch", location_guid) // old SDK sends location_guid as guid_branch
            parameter("item_guid", item_guid)
            parameter("item_code", item_code)
            parameter("ean_code", ean_code)
            parameter("serial_number", serial_number)
        }.body()

    suspend fun deleteGenDocScanLine(guid: String): BasicApiResponseModel<Boolean> =
        client.delete(GenericDocScanLineUrlPrefix.Prefix + "/backoffice-ep/" + guid.encodeURLPathPart()).body()

    /* InventoryItemController */

    suspend fun getInventoryItemByGuid(guid: String): BasicApiResponseModel<InventoryItemModel> =
        InvItemService(client).getInventoryItemByGuid(guid)

    suspend fun getInventoryItemFromItemCode(item_code: String = "", location_guid: String = ""): ApiResponseModel<InventoryItemModel> =
        InvItemService(client).getInventoryItemFromItemCode(item_code, location_guid)

    suspend fun getInventoryItemFromSerialOrBatch(scan_code: String = "", location_guid: String = ""): ApiResponseModel<InventoryItemModel> =
        InvItemService(client).getInventoryItemFromSerialOrBatch(scan_code, location_guid)

    /* SerialNumberController */

    /** Was Sandwich ApiResponse in the old SDK: errors are returned as [ApiResult], not thrown. */
    suspend fun getSerialNumbersByCriteria(
        sn_id: String = "",
        sn_ids: Set<String> = emptySet(),
        hdr_item_guid: String = "",
        location_guid: String = "",
        quantity_balance_greater_than: Int = 0,
        calcTotalRecords: Boolean = true,
        limit: Int = 100,
        offset: Int = 0
    ): ApiResult<PagingResponseModel<SerialNumberModel>> = SerialNumberService(client).getSerialNumbersByCriteria(
        sn_id, sn_ids, hdr_item_guid, location_guid, quantity_balance_greater_than, calcTotalRecords, limit, offset
    )
}
