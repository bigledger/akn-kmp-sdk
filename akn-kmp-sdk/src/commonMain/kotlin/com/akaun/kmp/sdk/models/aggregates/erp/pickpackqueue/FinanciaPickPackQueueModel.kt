package com.akaun.kmp.sdk.models.aggregates.erp.pickpackqueue

import com.akaun.kmp.sdk.models.dbschema.bl_fi_pick_pack_queue
import kotlinx.serialization.Serializable

@Serializable
data class FinanciaPickPackQueueModel(
    val bl_fi_pick_pack_queue: bl_fi_pick_pack_queue
)
