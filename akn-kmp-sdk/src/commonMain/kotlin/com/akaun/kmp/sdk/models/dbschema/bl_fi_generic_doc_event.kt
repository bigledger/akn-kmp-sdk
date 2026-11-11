package com.akaun.kmp.sdk.models.dbschema

import com.akaun.kmp.sdk.models.dbschema.intermediate.MessageJson
import kotlin.time.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_fi_generic_doc_event(
    var acl_config: JsonObject? = null,
    var acl_policy: JsonObject? = null,
    var action: String? = null,
    var applet_guid: String? = null,
    var created_by_subject_guid: String? = null,
    var date_completed: Instant? = null,
    var date_created: Instant? = null,
    var date_txn: Instant? = null,
    var descr: String? = null,
    var event_code: String? = null,
    var foreign_key: String? = null,
    var foreign_table: String? = null,
    var guid: String? = null,
    var guid_doc_hdr: String? = null,
    var guid_doc_line: String? = null,
    var id_device: String? = null,
    var id_user: String? = null,
    var id_user_agent: String? = null,
    var link_guid: String? = null,
    var message_json: JsonObject? = null,
    var module_guid: String? = null,
    var namespace: String? = null,
    var property_json: JsonObject? = null,
    var revision: String? = null,
    var status: String? = null,
    var txn_type: String? = null,
    var updated_by_subject_guid: String? = null,
    var vrsn: String? = null
)
