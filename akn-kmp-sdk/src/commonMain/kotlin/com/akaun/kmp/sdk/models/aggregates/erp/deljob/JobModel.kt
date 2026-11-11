package com.akaun.kmp.sdk.models.aggregates.erp.deljob

import com.akaun.kmp.sdk.models.dbschema.bl_del_job_attachment
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_event
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_ext
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_hdr
import kotlinx.serialization.Serializable

@Serializable
data class JobModel(
    val bl_del_job_attachments: List<bl_del_job_attachment>,
    val bl_del_job_event: List<bl_del_job_event>,
    val bl_del_job_ext: List<bl_del_job_ext>,
    val bl_del_job_hdr: bl_del_job_hdr
)
