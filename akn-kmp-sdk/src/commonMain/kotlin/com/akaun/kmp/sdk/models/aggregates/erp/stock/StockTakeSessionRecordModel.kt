package com.akaun.kmp.sdk.models.aggregates.erp.stock

import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_record
import kotlinx.serialization.Serializable

@Serializable
data class StockTakeSessionRecordModel (
    val bl_inv_stock_take_session_record: bl_inv_stock_take_session_record = bl_inv_stock_take_session_record()
)
