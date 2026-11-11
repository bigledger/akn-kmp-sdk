package com.akaun.kmp.sdk.models.aggregates.erp.stock

import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_device
import kotlinx.serialization.Serializable

@Serializable
data class StockTakeSessionDeviceModel (
    val bl_inv_stock_take_session_device: bl_inv_stock_take_session_device = bl_inv_stock_take_session_device()
)
