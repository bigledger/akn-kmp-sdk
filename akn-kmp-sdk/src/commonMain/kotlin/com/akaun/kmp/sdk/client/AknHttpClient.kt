package com.akaun.kmp.sdk.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

private const val TIMEOUT_MILLIS = 30_000L

/**
 * Creates the HTTP client every akaun service uses.
 *
 * @param baseUrl API root ending with `/`, e.g. `Core2Config.CLOUD_URL`. Service paths are relative to it.
 * @param authorization sent as the `Authorization` header when not null.
 * @param tenantCode sent as the `tenantCode` header when not null.
 * @param logLevel request/response logging; `Authorization` is always masked. Avoid [LogLevel.BODY]
 *   in production builds: it logs request bodies, including login passwords.
 * @param engine override the platform engine (OkHttp on Android/JVM, Darwin on iOS); tests pass a MockEngine.
 *
 * Non-2xx responses throw [io.ktor.client.plugins.ResponseException], like the old Retrofit suspend functions.
 */
fun createAknHttpClient(
    baseUrl: String,
    authorization: String? = null,
    tenantCode: String? = null,
    logLevel: LogLevel = LogLevel.NONE,
    engine: HttpClientEngine? = null,
): HttpClient {
    val config: HttpClientConfig<*>.() -> Unit = {
        expectSuccess = true
        install(ContentNegotiation) { json(AknJson) }
        // Same as the old OkHttp client: 30 s connect/read/write, no cap on the whole request (file downloads)
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT_MILLIS
            socketTimeoutMillis = TIMEOUT_MILLIS
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = logLevel
            sanitizeHeader { it == HttpHeaders.Authorization }
        }
        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
            authorization?.let { header(HttpHeaders.Authorization, it) }
            tenantCode?.let { header("tenantCode", it) }
        }
    }
    return if (engine != null) HttpClient(engine, config) else HttpClient(config)
}
