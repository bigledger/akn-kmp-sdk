package com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem

import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_inv_serial_number_event
import com.akaun.kmp.sdk.models.dbschema.bl_inv_serial_number_ext
import com.akaun.kmp.sdk.models.dbschema.bl_inv_serial_number_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_inv_serial_number_line
import kotlinx.serialization.Serializable

@Serializable
data class SerialNumberModel(
    val bl_inv_serial_number_hdr: bl_inv_serial_number_hdr = bl_inv_serial_number_hdr(),
    val bl_inv_serial_number_lineList: List<bl_inv_serial_number_line> = emptyList(),
    val bl_inv_serial_number_extList: List<bl_inv_serial_number_ext> = emptyList(),
    val bl_inv_serial_number_eventList: List<bl_inv_serial_number_event> = emptyList()
)
