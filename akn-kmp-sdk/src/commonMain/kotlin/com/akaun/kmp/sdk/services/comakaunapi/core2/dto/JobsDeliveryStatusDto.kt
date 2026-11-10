package com.akaun.kmp.sdk.services.comakaunapi.core2.dto

import com.akaun.kmp.sdk.models.dbschema.bl_del_job_dochdr_link
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_docline_link
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_event
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_shipment_link
import kotlinx.serialization.Serializable

@Serializable
data class JobsDeliveryStatusDto(
    val bl_del_job_hdr: bl_del_job_hdr,
    val bl_del_job_dochdr_links: List<bl_del_job_dochdr_link>,
    val bl_del_job_docline_links: List<bl_del_job_docline_link>,
    val bl_del_job_shipment_links: List<bl_del_job_shipment_link>,
    val bl_del_job_event: bl_del_job_event
)
