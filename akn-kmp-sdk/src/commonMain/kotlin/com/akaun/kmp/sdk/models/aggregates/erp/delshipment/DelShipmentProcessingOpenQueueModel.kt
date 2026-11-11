package com.akaun.kmp.sdk.models.aggregates.erp.delshipment

import com.akaun.kmp.sdk.models.dbschema.bl_del_shipment_processing_open_queue
import kotlinx.serialization.Serializable

@Serializable
data class DelShipmentProcessingOpenQueueModel(
    val bl_del_shipment_processing_open_queue: bl_del_shipment_processing_open_queue
)
