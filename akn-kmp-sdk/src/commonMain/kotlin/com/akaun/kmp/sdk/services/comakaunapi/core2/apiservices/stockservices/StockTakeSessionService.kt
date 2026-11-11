package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices

import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionHeaderModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.encodeURLPathPart

private const val SESSIONS = Core2Config.DOMAIN_URL_PREFIX + "stocktake-hdr"

class StockTakeSessionService(private val client: HttpClient) {

    suspend fun getAllStockTakeSessionHdr(): ApiResponseModel<StockTakeSessionHeaderModel> =
        client.get("$SESSIONS/backoffice-ep").body()

    suspend fun getStockTakeSessionHdrByGuid(guid: String): BasicApiResponseModel<StockTakeSessionHeaderModel> =
        client.get("$SESSIONS/backoffice-ep/" + guid.encodeURLPathPart()).body()

    suspend fun getStockTakeSessionByDeviceGuid(
        device_guid: String = "",
        is_session_open: Boolean = true
    ): ApiResponseModel<StockTakeSessionHeaderModel> =
        client.get("$SESSIONS/device-session/backoffice-ep/" + device_guid.encodeURLPathPart() + "/" + is_session_open).body()

    suspend fun createStockTakeSessionHdr(stockTakeSessionHdrModel: StockTakeSessionHeaderModel): BasicApiResponseModel<StockTakeSessionHeaderModel> =
        client.post("$SESSIONS/backoffice-ep") { setBody(stockTakeSessionHdrModel) }.body()

    /** Fixed: the old SDK typed this as session-device models, so every header decoded as an empty object. */
    suspend fun getStockTakeSessionHdrByQuery(
        calcTotalRecords: Boolean = true,
        orderBy: String = "",
        order: String = "ASC",
        limit: Int = 100,
        offset: Int = 0
    ): PagingResponseModel<StockTakeSessionHeaderModel> =
        client.get("$SESSIONS/backoffice-ep/query") {
            parameter("calcTotalRecords", calcTotalRecords)
            parameter("orderBy", orderBy)
            parameter("order", order)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()
}
