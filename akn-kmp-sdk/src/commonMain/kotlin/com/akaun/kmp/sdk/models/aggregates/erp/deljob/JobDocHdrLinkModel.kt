package com.akaun.kmp.sdk.models.aggregates.erp.deljob

import com.akaun.kmp.sdk.models.dbschema.bl_del_job_dochdr_link
import kotlinx.serialization.Serializable

@Serializable
data class JobDocHdrLinkModel(
    val bl_del_job_dochdr_link: bl_del_job_dochdr_link
)
