package com.akaun.kmp.sdk.services.comakaunapi.core2.dto

import kotlinx.serialization.Serializable

@Serializable
data class InventoryItemOfflineDto(
    val bl_inv_mst_item_hdr: InventoryItemHdrOfflineDto = InventoryItemHdrOfflineDto(),
    val bl_inv_mst_item_lines: List<InventoryItemLineOfflineDto> = emptyList()
)

@Serializable
data class InventoryItemHdrOfflineDto(
    var guid: String? = null,
    var txn_type: String? = null,
    var code: String? = null,
    var name: String? = null,
    var uom: String? = null,
    var guid_fi_mst_item: String? = null,
    var status: String? = null,
    var sub_item_type: String? = null,
    var scan_code: String? = null,
)

@Serializable
data class InventoryItemLineOfflineDto(
    var guid: String? = null,
    var hdr_guid: String? = null,
    var parameter_code: String? = null,
    var status: String? = null,
    var code: String? = null,
    var uom: String? = null,
    var quantity: Double? = null,
    var scan_code: String? = null,
)
