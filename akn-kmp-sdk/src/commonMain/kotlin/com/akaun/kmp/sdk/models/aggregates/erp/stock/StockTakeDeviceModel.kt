package com.akaun.kmp.sdk.models.aggregates.erp.stock

import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_device
import kotlinx.serialization.Serializable

@Serializable
data class StockTakeDeviceModel (
    val bl_inv_stock_take_device: bl_inv_stock_take_device = bl_inv_stock_take_device()
)
