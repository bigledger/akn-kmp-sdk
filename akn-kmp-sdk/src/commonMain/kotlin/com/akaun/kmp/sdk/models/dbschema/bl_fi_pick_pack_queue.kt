package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable

@Serializable
data class bl_fi_pick_pack_queue(
    var branch_code: String? = null,
    var client_doc_type_1: String? = null,
    var company_code: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: String? = null,
    var doc_entity_hdr_guid: String? = null,
    var fi_item_code: String? = null,
    var fi_item_guid: String? = null,
    var flow_type: String? = null,
    var guid: String? = null,
    var guid_branch: String? = null,
    var guid_comp: String? = null,
    var guid_doc_1_hdr: String? = null,
    var guid_doc_1_line: String? = null,
    var guid_store: String? = null,
    var inv_item_code: String? = null,
    var inv_item_guid: String? = null,
    var location_code: String? = null,
    var qty_balance: Double? = null,
    var revision: String? = null,
    var server_doc_type_1: String? = null,
    var status: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: String? = null
)
