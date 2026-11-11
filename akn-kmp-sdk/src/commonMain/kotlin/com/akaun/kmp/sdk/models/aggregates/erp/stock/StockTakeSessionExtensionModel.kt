package com.akaun.kmp.sdk.models.aggregates.erp.stock

import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_ext
import kotlinx.serialization.Serializable

@Serializable
data class StockTakeSessionExtensionModel(
    val bl_inv_stock_take_session_ext: bl_inv_stock_take_session_ext = bl_inv_stock_take_session_ext()
)
