package com.akaun.kmp.sdk.models.aggregates.erp.deljob

import com.akaun.kmp.sdk.models.dbschema.bl_del_job_attachment
import kotlinx.serialization.Serializable

@Serializable
data class JobAttachmentModel (
    val bl_del_job_attachment : bl_del_job_attachment
)
