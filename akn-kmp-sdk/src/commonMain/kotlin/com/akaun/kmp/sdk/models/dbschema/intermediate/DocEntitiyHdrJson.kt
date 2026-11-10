package com.akaun.kmp.sdk.models.dbschema.intermediate

import kotlinx.serialization.Serializable

@Serializable
data class DocEntityHdrJson(
    val entityId: String?,
    val entityName: String?,
    val entityType: String?,
    val glCode: String?,
    val idNumber: String?,
    val identityType: String?,
    val salesAgent: String?,
    val salesLead: String?,
    val status: String?
)
