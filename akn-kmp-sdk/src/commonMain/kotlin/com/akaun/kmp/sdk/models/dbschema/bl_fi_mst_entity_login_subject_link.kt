package com.akaun.kmp.sdk.models.dbschema

import kotlinx.serialization.Serializable

@Serializable
data class bl_fi_mst_entity_login_subject_link(
    val created_by_subject_guid: String,
    val created_date: String,
    val entity_hdr_guid: String,
    val entity_line_guid: String,
    val guid: String,
    val rank: String,
    val revision: String,
    val status: String,
    val subject_guid: String,
    val title: String,
    val updated_by_subject_guid: String,
    val updated_date: String,
    val vrsn: String
)
