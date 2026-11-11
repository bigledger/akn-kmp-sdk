package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices

import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.InventoryItemModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodeURLPathPart

object Core2UrlInvItemPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.INV_MODULE_PREFIX + "inv-items/"
}

class InvItemService(private val client: HttpClient) {

    suspend fun getInventoryItemByGuid(guid: String): BasicApiResponseModel<InventoryItemModel> =
        client.get(Core2UrlInvItemPrefix.PREFIX + "backoffice-ep/" + guid.encodeURLPathPart()).body()

    suspend fun getInventoryItemFromItemCode(
        item_code: String = "",
        location_guid: String = ""
    ): ApiResponseModel<InventoryItemModel> =
        client.get(Core2UrlInvItemPrefix.PREFIX + "stock-take/items/backoffice-ep") {
            parameter("item_code", item_code)
            parameter("location_guid", location_guid)
        }.body()

    suspend fun getInventoryItemFromSerialOrBatch(
        scan_code: String = "",
        location_guid: String = ""
    ): ApiResponseModel<InventoryItemModel> =
        client.get(Core2UrlInvItemPrefix.PREFIX + "stock-take/serial-batch/backoffice-ep") {
            parameter("scan_code", scan_code)
            parameter("location_guid", location_guid)
        }.body()

    suspend fun getInventoryItemByCriteria(
        calcTotalRecords: Boolean = true,
        limit: Int = 10,
        offset: Int = 0,
        name: String? = null,
        code: String? = null,
    ): ApiResponseModel<InventoryItemModel> =
        client.get(Core2UrlInvItemPrefix.PREFIX + "backoffice-ep/query") {
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("limit", limit)
            parameter("offset", offset)
            parameter("name", name)
            parameter("code", code)
        }.body()

    suspend fun getInventoryItemByNameAndCode(
        name: String? = null,
        code: String? = null,
        offset: Int = 0,
        limit: Int = 10,
    ): ApiResponseModel<InventoryItemModel> =
        client.get(Core2UrlInvItemPrefix.PREFIX + "search-name-code/backoffice-ep") {
            // Backend requires both params (an empty value is accepted); omitting a null one gave HTTP 400
            parameter("name", name ?: "")
            parameter("code", code ?: "")
            parameter("offset", offset)
            parameter("limit", limit)
        }.body()
}
