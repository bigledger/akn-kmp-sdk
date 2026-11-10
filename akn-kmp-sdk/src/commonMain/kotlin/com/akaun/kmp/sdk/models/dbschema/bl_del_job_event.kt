package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class bl_del_job_event(
    var guid: String? = null,
    var guid_job_hdr: String? = null,
    var guid_job_dochdr_link: String? = null,
    var guid_job_docline_link: String? = null,
    var guid_job_shipment_link: String? = null,
    var txn_type: String? = null,
    var action: String? = null,
    var descr: String? = null,
    var message_json: JsonObject? = null,
    var completed_date: JsonElement? = null,
    var txn_date: JsonElement? = null,
    var event_code: String? = null,
    var id_user: String? = null,
    var id_device: String? = null,
    var id_user_agent: String? = null,
    var foreign_table: String? = null,
    var foreign_key: String? = null,
    var revision: String? = null,
    var vrsn: String? = null,
    var namespace: String? = null,
    var module_guid: String? = null,
    var applet_guid: String? = null,
    var property_json: JsonElement? = null,
    var acl_config: JsonElement? = null,
    var acl_policy: JsonElement? = null,
    var created_date: JsonElement? = null,
    var updated_date: JsonElement? = null,
    var created_by_subject_guid: String? = null,
    var updated_by_subject_guid: String? = null,
    var status: String? = null
)
