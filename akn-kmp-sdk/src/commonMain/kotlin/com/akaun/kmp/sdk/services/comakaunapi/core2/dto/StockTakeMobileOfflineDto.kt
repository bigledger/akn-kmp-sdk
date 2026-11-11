package com.akaun.kmp.sdk.services.comakaunapi.core2.dto

import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_device
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_device
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_hdr
import kotlinx.serialization.Serializable

@Serializable
data class StockTakeMobileOfflineDto (
    val bl_inv_stock_take_device: bl_inv_stock_take_device = bl_inv_stock_take_device(),
    val bl_inv_stock_take_session_hdrs: List<bl_inv_stock_take_session_hdr> = emptyList(),
    val bl_inv_stock_take_session_devices: List<bl_inv_stock_take_session_device> = emptyList()
)
