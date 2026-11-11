package com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared

/** A file to upload in a multipart request (replaces okhttp3.MultipartBody.Part, which is JVM-only). */
class FileUpload(
    val fileName: String,
    val bytes: ByteArray,
    val contentType: String,
)
