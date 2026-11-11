package com.akaun.kmp.sdk.models.aggregates.erp.appletloginsubject

import com.akaun.kmp.sdk.models.dbschema.bl_applet_login_subject_link
import com.akaun.kmp.sdk.models.dbschema.bl_applet_login_subject_link_ext
import kotlinx.serialization.Serializable

@Serializable
data class AppletLoginSubjectLinkModel(
    val bl_applet_login_subject_link: bl_applet_login_subject_link,
    val bl_applet_login_subject_link_ext: List<bl_applet_login_subject_link_ext>
)
