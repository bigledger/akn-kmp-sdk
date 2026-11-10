package com.akaun.kmp.sdk.models.aggregates.erp.company

import com.akaun.kmp.sdk.models.dbschema.bl_fi_mst_comp
import com.akaun.kmp.sdk.models.dbschema.bl_fi_mst_comp_ext
import kotlinx.serialization.Serializable

@Serializable
data class CompanyModel(
    val bl_fi_mst_comp: bl_fi_mst_comp = bl_fi_mst_comp(),
    val bl_fi_mst_comp_ext: List<bl_fi_mst_comp_ext> = emptyList()
)
