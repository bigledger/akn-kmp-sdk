package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices

import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.InventoryItemOfflineDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.StockTakeMobileOfflineDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.encodeURLPathPart

private const val OFFLINE = Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + Core2Config.STOCKTAKE_PREFIX + "offline/"

class StockTakeOfflineService(private val client: HttpClient) {

    suspend fun getAllOfflineStockTakeInfoForDevice(device_code: String): BasicApiResponseModel<StockTakeMobileOfflineDto> =
        client.get(OFFLINE + "device-stocktake-info/" + device_code.encodeURLPathPart()).body()

    suspend fun getInventoryItemsForOffline(): ApiResponseModel<InventoryItemOfflineDto> =
        client.get(OFFLINE + "inv-items").body()

    suspend fun createMultiStokeTakeSessionRecord(
        stockTakeSessionRecordModels: List<StockTakeSessionRecordModel>
    ): ApiResponseModel<StockTakeSessionRecordModel> =
        client.post(Core2Config.DOMAIN_URL_PREFIX + "stocktake-record/multi") { setBody(stockTakeSessionRecordModels) }.body()
}
