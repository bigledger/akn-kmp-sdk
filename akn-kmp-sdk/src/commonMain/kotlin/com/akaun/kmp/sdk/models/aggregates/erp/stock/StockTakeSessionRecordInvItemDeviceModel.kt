package com.akaun.kmp.sdk.models.aggregates.erp.stock

import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_ext
import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_line
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_device
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_record
import kotlinx.serialization.Serializable

@Serializable
data class StockTakeSessionRecordInvItemDeviceModel(
    val bl_inv_stock_take_session_record: bl_inv_stock_take_session_record = bl_inv_stock_take_session_record(),
    val bl_inv_stock_take_device: bl_inv_stock_take_device = bl_inv_stock_take_device(),
    val bl_inv_mst_item_hdr: bl_inv_mst_item_hdr = bl_inv_mst_item_hdr(),
    val bl_inv_mst_item_exts: List<bl_inv_mst_item_ext> = emptyList(),
    val bl_inv_mst_item_lines: List<bl_inv_mst_item_line> = emptyList()
)
