package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices

import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionDeviceModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.encodeURLPathPart

private const val DEVICES = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.STOCKTAKE_PREFIX + "devices/backoffice-ep"
private const val SESSION_DEVICES = Core2Config.DOMAIN_URL_PREFIX + "stocktake-device"

class StockTakeDeviceService(private val client: HttpClient) {

    suspend fun createStockTakeDevice(stockTakeDeviceModel: StockTakeDeviceModel): BasicApiResponseModel<StockTakeDeviceModel> =
        client.post(DEVICES) { setBody(stockTakeDeviceModel) }.body()

    suspend fun updateStockTakeDevice(stockTakeDeviceModel: StockTakeDeviceModel): BasicApiResponseModel<StockTakeDeviceModel> =
        client.put(DEVICES) { setBody(stockTakeDeviceModel) }.body()

    suspend fun getAllStockTakeDevice(): ApiResponseModel<StockTakeDeviceModel> =
        client.get(DEVICES).body()

    suspend fun getStockTakeDeviceByGuid(guid: String): BasicApiResponseModel<StockTakeDeviceModel> =
        client.get(DEVICES + "/" + guid.encodeURLPathPart()).body()

    suspend fun getStockTakeDeviceByCriteria(
        calcTotalRecords: Boolean = true,
        code: String = "",
        limit: Int = 100,
        offset: Int = 0
    ): PagingResponseModel<StockTakeDeviceModel> =
        client.get("$DEVICES/query") {
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("code", code)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getStockTakeSessionDeviceByCriteria(
        calcTotalRecords: Boolean = true,
        sessionGuid: String = "",
        deviceGuid: String = "",
        limit: Int = 100,
        offset: Int = 0
    ): PagingResponseModel<StockTakeSessionDeviceModel> =
        client.get("$SESSION_DEVICES/query") {
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("session_guid", sessionGuid)
            parameter("device_guid", deviceGuid)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    /**
     * Fixed: the old SDK called ".../query" with an unused @Path (Retrofit rejected it on every call) and
     * declared a list result. The backend route is GET stocktake-device/{guid}, returning one record.
     */
    suspend fun getStockTakeSessionDeviceByGuid(guid: String): BasicApiResponseModel<StockTakeSessionDeviceModel> =
        client.get(SESSION_DEVICES + "/" + guid.encodeURLPathPart()).body()

    suspend fun createStockTakeSessionDevice(stockTakeSessionDeviceModel: StockTakeSessionDeviceModel): BasicApiResponseModel<StockTakeSessionDeviceModel> =
        client.post(SESSION_DEVICES) { setBody(stockTakeSessionDeviceModel) }.body()

    suspend fun updateStockTakeSessionDevice(stockTakeSessionDeviceModel: StockTakeSessionDeviceModel): BasicApiResponseModel<StockTakeSessionDeviceModel> =
        client.put(SESSION_DEVICES) { setBody(stockTakeSessionDeviceModel) }.body()

    suspend fun deleteStockTakeSessionDevice(guid: String): BasicApiResponseModel<Boolean> =
        client.delete(SESSION_DEVICES + "/" + guid.encodeURLPathPart()).body()
}
