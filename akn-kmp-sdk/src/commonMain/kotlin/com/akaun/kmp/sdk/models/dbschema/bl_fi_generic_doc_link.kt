package com.akaun.kmp.sdk.models.dbschema

import com.akaun.kmp.sdk.models.dbschema.intermediate.LogJson
import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_fi_generic_doc_link(
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var amount_contra: Double? = null,
    var amount_signum: Double? = null,
    var applet_guid: String? = null,
    var batch_no: JsonObject? = null,
    var bin_no: JsonObject? = null,
    var client_doc_status_01: String? = null,
    var client_doc_status_02: String? = null,
    var client_doc_status_03: String? = null,
    var client_doc_status_04: String? = null,
    var client_doc_status_05: String? = null,
    var created_by_subject_guid: String? = null,
    var created_date: Instant? = null,
    var date_doc_1_hdr: Instant? = null,
    var date_doc_1_line: Instant? = null,
    var date_doc_2_hdr: Instant? = null,
    var date_doc_2_line: Instant? = null,
    var date_txn: Instant? = null,
    var descr: String? = null,
    var guid: String? = null,
    var guid_doc_1_hdr: String? = null,
    var guid_doc_1_line: String? = null,
    var guid_doc_2_hdr: String? = null,
    var guid_doc_2_line: String? = null,
    var guid_doc_event: String? = null,
    var log_json: JsonObject? = null,
    var logic: String? = null,
    var module_guid: String? = null,
    var namespace: String? = null,
    var option: String? = null,
    var property_json: JsonObject? = null,
    var quantity_contra: Double? = null,
    var quantity_signum: Double? = null,
    var remarks: String? = null,
    var revision: String? = null,
    var serial_no: JsonObject? = null,
    var server_doc_type_doc_1_hdr: String? = null,
    var server_doc_type_doc_1_line: String? = null,
    var server_doc_type_doc_2_hdr: String? = null,
    var server_doc_type_doc_2_line: String? = null,
    var status: String? = null,
    var txn_type: String? = null,
    var updated_by_subject_guid: String? = null,
    var updated_date: Instant? = null,
    var vrsn: String? = null
)
