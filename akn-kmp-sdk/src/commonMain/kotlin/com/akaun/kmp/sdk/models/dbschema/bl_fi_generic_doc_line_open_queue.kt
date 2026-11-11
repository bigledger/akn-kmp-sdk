package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_fi_generic_doc_line_open_queue(
    var guid: String? = null,
    var flow_type: String? = null,
    var guid_doc_1_hdr: String? = null,
    var guid_doc_1_line: String? = null,
    var server_doc_type_1: String? = null,
    var server_doc_type_2: String? = null,
    var qty_open: String? = null,
    var doc_1_line_fi_item_guid: String? = null,
    var fi_item_code: String? = null,
    var inv_item_guid: String? = null,
    var inv_item_code: String? = null,
    var guid_comp: String? = null,
    var company_code: String? = null,
    var guid_branch: String? = null,
    var branch_code: String? = null,
    var guid_store: String? = null,
    var location_code: String? = null,
    var guid_store_2: String? = null,
    var location_code_2: String? = null,
    var doc_entity_hdr_guid: String? = null,
    var namespace: String? = null,
    var module_guid: String? = null,
    var applet_guid: String? = null,
    var property_json: JsonElement? = null,
    var acl_config: JsonElement? = null,
    var acl_policy: JsonElement? = null,
    var status: String? = null,
    var created_date: String? = null,
    var updated_date: String? = null,
    var created_by_subject_guid: String? = null,
    var updated_by_subject_guid: String? = null,
    var revision: String? = null,
    var vrsn: String? = null,
    var tracking_id: String? = null,
    var allocation_qty: String? = null,
    var approval_status: String? = null,
    var serial_no: JsonElement? = null,
    var bin_no: JsonElement? = null,
    var batch_no: JsonElement? = null
)
