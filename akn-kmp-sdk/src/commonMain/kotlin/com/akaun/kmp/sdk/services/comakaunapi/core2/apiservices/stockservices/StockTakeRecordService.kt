package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices

import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.InventoryItemModel
import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.SerialNumberModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordInvItemDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordModel
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

private const val RECORDS = Core2Config.DOMAIN_URL_PREFIX + "stocktake-record"

class StockTakeRecordService(private val client: HttpClient) {

    suspend fun createStokeTakeSessionRecord(stockTakeSessionRecordModel: StockTakeSessionRecordModel): ApiResponseModel<StockTakeSessionRecordModel> =
        client.post(RECORDS) { setBody(stockTakeSessionRecordModel) }.body()

    /** Fixed: takes a list. The backend /multi reads a JSON array; the old SDK sent one object and got HTTP 400. */
    suspend fun createMultiStokeTakeSessionRecord(stockTakeSessionRecordModels: List<StockTakeSessionRecordModel>): ApiResponseModel<StockTakeSessionRecordModel> =
        client.post("$RECORDS/multi") { setBody(stockTakeSessionRecordModels) }.body()

    suspend fun updateStokeTakeSessionRecord(stockTakeSessionRecordModel: StockTakeSessionRecordModel): ApiResponseModel<StockTakeSessionRecordModel> =
        client.put(RECORDS) { setBody(stockTakeSessionRecordModel) }.body()

    /** Fixed: takes a list. The backend /multi reads a JSON array; the old SDK sent one object and got HTTP 400. */
    suspend fun updateMultiStokeTakeSessionRecord(stockTakeSessionRecordModels: List<StockTakeSessionRecordModel>): ApiResponseModel<StockTakeSessionRecordModel> =
        client.put("$RECORDS/multi") { setBody(stockTakeSessionRecordModels) }.body()

    suspend fun deleteStokeTakeSessionRecord(guid: String): BasicApiResponseModel<Boolean> =
        client.delete("$RECORDS/" + guid.encodeURLPathPart()).body()

    suspend fun getAllStokeTakeSessionRecord(): ApiResponseModel<StockTakeSessionRecordModel> =
        client.get(RECORDS).body()

    suspend fun getStokeTakeSessionRecordByGuid(guid: String): BasicApiResponseModel<StockTakeSessionRecordModel> =
        client.get("$RECORDS/" + guid.encodeURLPathPart()).body()

    suspend fun getStokeTakeSessionRecordByCriteria(
        guid_session_hdr: String = "",
        guid_session_device: String = "",
        calcTotalRecords: Boolean = true,
        limit: Int = 100,
        offset: Int = 0
    ): PagingResponseModel<StockTakeSessionRecordModel> =
        client.get("$RECORDS/query") {
            parameter("guid_session_hdr", guid_session_hdr)
            parameter("guid_session_device", guid_session_device)
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getStockTakeRecordWithInvItemByCriteria(
        guid_session_hdr: String = "",
        guid_session_device: String = "",
        calcTotalRecords: Boolean = true,
        limit: Int = 100,
        offset: Int = 0
    ): PagingResponseModel<StockTakeSessionRecordInvItemDeviceModel> =
        client.get("$RECORDS/inv-item-devices/query") {
            parameter("guid_session_hdr", guid_session_hdr)
            parameter("guid_session_device", guid_session_device)
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getInventoryItemFromItemCode(item_code: String = "", location_guid: String = ""): ApiResponseModel<InventoryItemModel> =
        InvItemService(client).getInventoryItemFromItemCode(item_code, location_guid)

    suspend fun getInventoryItemFromSerialOrBatch(scan_code: String = "", location_guid: String = ""): ApiResponseModel<InventoryItemModel> =
        InvItemService(client).getInventoryItemFromSerialOrBatch(scan_code, location_guid)

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
