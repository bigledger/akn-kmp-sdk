package com.akaun.kmp.sdk.models.aggregates.erp.gendoc

import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_scan_line
import kotlinx.serialization.Serializable

@Serializable
data class GenericDocumentScanLineModel(
    val bl_fi_generic_doc_scan_line: bl_fi_generic_doc_scan_line = bl_fi_generic_doc_scan_line()
)
