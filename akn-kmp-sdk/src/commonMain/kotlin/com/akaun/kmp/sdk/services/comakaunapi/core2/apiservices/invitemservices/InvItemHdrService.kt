package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices

import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.InventoryItemHeaderModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

object Core2UrlInvItemHdrPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "erp/" + "inv/" + "inv-items/hdrs/"
}

class InvItemHdrService(private val client: HttpClient) {

    suspend fun getInventoryItemHdrsByCriteria(mstItemGuid: String): PagingResponseModel<InventoryItemHeaderModel> =
        client.get(Core2UrlInvItemHdrPrefix.PREFIX + "query") {
            parameter("guid_fi_mst_item", mstItemGuid)
        }.body()
}
