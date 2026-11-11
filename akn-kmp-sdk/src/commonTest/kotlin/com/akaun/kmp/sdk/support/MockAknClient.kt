package com.akaun.kmp.sdk.support

import com.akaun.kmp.sdk.client.createAknHttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

/**
 * Test harness for models and services: a real [createAknHttpClient] backed by a MockEngine.
 * Every request is answered with [responseJson] / [status] and recorded in [requests] for assertions.
 *
 * Usage: `val mock = MockAknClient(responseJson = fixture); LoginService(mock.client).login(...); mock.requests.single()`
 */
class MockAknClient(
    responseJson: String = "{}",
    status: HttpStatusCode = HttpStatusCode.OK,
    authorization: String? = null,
    tenantCode: String? = null,
) {
    val requests = mutableListOf<HttpRequestData>()

    val client = createAknHttpClient(
        baseUrl = BASE_URL,
        authorization = authorization,
        tenantCode = tenantCode,
        engine = MockEngine { request ->
            requests += request
            respond(responseJson, status, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        },
    )

    companion object {
        const val BASE_URL = "https://api.test.akaun/"
    }
}

/** The request body as sent on the wire (JSON text for normal requests). */
suspend fun HttpRequestData.bodyText(): String = body.toByteArray().decodeToString()
