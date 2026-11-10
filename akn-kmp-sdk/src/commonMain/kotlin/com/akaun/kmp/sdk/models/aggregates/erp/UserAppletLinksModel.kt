package com.akaun.kmp.sdk.models.aggregates.erp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAppletLinkResponse(
    val guid: String,
    val user_applet_rank: String,
    val applet_catalog_link_guid: String,
    val user_applet_index: Int,
    val status: String,
    val applet: Applet,
    val catalog: Catalog,
    val tenant: Tenant,
    val store: Store,
    val isStore: Boolean,
    // Backend field is notification_count; the old SDK's name never matched it (always 0 under GSON)
    @SerialName("notification_count")
    val notificationCount: Int
)

@Serializable
data class Applet(
    val guid: String,
    val applet_mf_html_tag: String,
    val applet_code: String,
    val applet_shortcode: String,
    val applet_icon_guid: String,
    val applet_name: String,
    val applet_description: String,
    val es_module_url: String,
    val routerlink: String,
    val store_guid: String,
    val status: String
)

@Serializable
data class Catalog(
    val guid: String,
    val catalog_code: String,
    val catalog_name: String,
    val status: String
)

@Serializable
data class Tenant(
    val guid: String,
    val tenant_code: String,
    val tenant_name: String,
    val status: String
)

@Serializable
data class Store(
    val guid: String,
    val code: String,
    val name: String
)
