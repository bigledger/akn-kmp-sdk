package com.akaun.kmp.sdk.models.aggregates.erp.delshipment

import com.akaun.kmp.sdk.models.dbschema.bl_del_job_shipment_link
import kotlinx.serialization.Serializable

@Serializable
data class DelJobShipmentLinkModel(
    val bl_del_job_shipment_link: bl_del_job_shipment_link
)
