package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.companyservices

import com.akaun.kmp.sdk.models.aggregates.erp.company.CompanyModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodeURLPathPart

class CompanyService(private val client: HttpClient) {

    suspend fun getCompanyByGuid(guid: String): BasicApiResponseModel<CompanyModel> =
        client.get(Core2Config.TENANT_DOMAIN_URL_PREFIX + Core2Config.ERP_MODULE_PREFIX + "companies/" + guid.encodeURLPathPart()).body()
}
