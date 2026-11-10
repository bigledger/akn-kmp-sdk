package com.akaun.kmp.sdk.models.aggregates.erp.gendoc

import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_ext
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_line
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_line_open_queue
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_link
import kotlinx.serialization.Serializable

@Serializable
data class GenericDocumentHdrLineLinkQueueListModel(
    val bl_fi_generic_doc_hdr : bl_fi_generic_doc_hdr,
    val bl_fi_generic_doc_lines : List<bl_fi_generic_doc_line>,
    val bl_fi_generic_doc_line_open_queues: List<bl_fi_generic_doc_line_open_queue>,
    val bl_fi_generic_doc_exts : List<bl_fi_generic_doc_ext>,
    val bl_fi_generic_doc_links:List<bl_fi_generic_doc_link>
)
