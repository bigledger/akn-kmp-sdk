package com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem

import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_ext
import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_line
import kotlinx.serialization.Serializable

@Serializable
data class InventoryItemModel(
    val bl_inv_mst_item_hdr: bl_inv_mst_item_hdr = bl_inv_mst_item_hdr(),
    val bl_inv_mst_item_exts: List<bl_inv_mst_item_ext> = emptyList(),
    val bl_inv_mst_item_lines: List<bl_inv_mst_item_line> = emptyList()
)
