package com.akaun.kmp.sdk.models.aggregates.erp.location

import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_location
import com.akaun.kmp.sdk.models.dbschema.bl_inv_mst_location_ext
import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val bl_inv_mst_location: bl_inv_mst_location = bl_inv_mst_location(),
    val bl_inv_mst_location_ext: List<bl_inv_mst_location_ext> = emptyList()
)
