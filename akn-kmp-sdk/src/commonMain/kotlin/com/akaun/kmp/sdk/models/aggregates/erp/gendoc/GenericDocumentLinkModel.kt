package com.akaun.kmp.sdk.models.aggregates.erp.gendoc

import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_link
import kotlinx.serialization.Serializable

@Serializable
data class GenericDocumentLinkModel (
    val bl_fi_generic_doc_link : bl_fi_generic_doc_link
)
