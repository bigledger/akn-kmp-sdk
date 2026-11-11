package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobAttachmentModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.Core2Config
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.FileUpload
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.encodeURLPathPart
import io.ktor.http.quote

object Core2UrlJobAttachmentPrefix {
    const val PREFIX = Core2Config.TENANT_DOMAIN_URL_PREFIX + "del/job-attachments"
}

class JobAttachmentService(private val client: HttpClient) {

    /** Multipart upload: each file goes in a "files" part, [data] goes in a JSON "data" part (backend @RequestPart). */
    suspend fun createJobAttachment(
        files: List<FileUpload>,
        data: JobAttachmentModel
    ): ApiResponseModel<JobAttachmentModel> =
        client.post(Core2UrlJobAttachmentPrefix.PREFIX + "/login-driver-ep") {
            setBody(MultiPartFormDataContent(formData {
                files.forEach { file ->
                    append("files", file.bytes, Headers.build {
                        append(HttpHeaders.ContentType, file.contentType)
                        append(HttpHeaders.ContentDisposition, "filename=${file.fileName.quote()}")
                    })
                }
                append("data", AknJson.encodeToString(JobAttachmentModel.serializer(), data), Headers.build {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                })
            }))
        }.body()

    /** Raw file bytes (was okhttp3.ResponseBody in the old SDK). */
    suspend fun getJobAttachmentFileByGuidLoginDriver(guid: String): ByteArray =
        client.get(Core2UrlJobAttachmentPrefix.PREFIX + "/login-driver-ep/file/" + guid.encodeURLPathPart()).body()
}
