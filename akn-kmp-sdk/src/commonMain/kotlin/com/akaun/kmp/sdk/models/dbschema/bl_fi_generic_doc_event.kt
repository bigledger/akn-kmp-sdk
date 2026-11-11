package com.akaun.kmp.sdk.models.dbschema

import com.akaun.kmp.sdk.models.dbschema.intermediate.MessageJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class bl_fi_generic_doc_event(
    var acl_config: JsonElement? = null,
    var acl_policy: JsonElement? = null,
    var action: String? = null,
    var applet_guid: JsonElement? = null,
    var created_by_subject_guid: String? = null,
    var date_completed: JsonElement? = null,
    var date_created: String? = null,
    var date_txn: String? = null,
    var descr: JsonElement? = null,
    var event_code: String? = null,
    var foreign_key: JsonElement? = null,
    var foreign_table: JsonElement? = null,
    var guid: String? = null,
    var guid_doc_hdr: String? = null,
    var guid_doc_line: JsonElement? = null,
    var id_device: JsonElement? = null,
    var id_user: JsonElement? = null,
    var id_user_agent: JsonElement? = null,
    var link_guid: JsonElement? = null,
    var message_json: JsonElement? = null,
    var module_guid: JsonElement? = null,
    var namespace: JsonElement? = null,
    var property_json: JsonElement? = null,
    var revision: String? = null,
    var status: String? = null,
    var txn_type: String? = null,
    var updated_by_subject_guid: String? = null,
    var vrsn: JsonElement? = null
)
