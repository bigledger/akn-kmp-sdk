package com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem

import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_item_hdr
import kotlinx.serialization.Serializable

@Serializable
data class InventoryItemHeaderModel(
    val bl_inv_mst_item_hdr: bl_inv_mst_item_hdr = bl_inv_mst_item_hdr()
)
