package com.akaun.kmp.sdk.models.aggregates.erp.gendoc

import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_event
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_ext
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_line
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_link
import kotlinx.serialization.Serializable

@Serializable
data class GenericDocumentModel(
    var bl_fi_generic_doc_hdr: bl_fi_generic_doc_hdr = bl_fi_generic_doc_hdr(),
    var bl_fi_generic_doc_ext: List<bl_fi_generic_doc_ext> = emptyList(),
    var bl_fi_generic_doc_line: List<bl_fi_generic_doc_line> = emptyList(),
    var bl_fi_generic_doc_event: List<bl_fi_generic_doc_event> = emptyList(),
    var bl_fi_generic_doc_link: List<bl_fi_generic_doc_link> = emptyList()
)
