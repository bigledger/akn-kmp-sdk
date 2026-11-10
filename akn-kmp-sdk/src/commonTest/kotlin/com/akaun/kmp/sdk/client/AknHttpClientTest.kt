package com.akaun.kmp.sdk.client

import com.akaun.kmp.sdk.support.MockAknClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

@Serializable
private data class Echo(val name: String? = null)

class AknHttpClientTest {

    @Test
    fun relativePathResolvesAgainstBaseUrlAndAuthHeadersAreSent() = runTest {
        val mock = MockAknClient(authorization = "Bearer abc", tenantCode = "staging_tenant")

        // Core2Config prefixes have no leading slash, exactly like this path
        mock.client.get("core2/platform/dm/applets/user-applet-links")

        val request = mock.requests.single()
        assertEquals("https://api.test.akaun/core2/platform/dm/applets/user-applet-links", request.url.toString())
        assertEquals("Bearer abc", request.headers[HttpHeaders.Authorization])
        assertEquals("staging_tenant", request.headers["tenantCode"])
    }

    @Test
    fun authHeadersAreAbsentWhenNotGiven() = runTest {
        val mock = MockAknClient()

        mock.client.get("core2/platform/dm/identity/login")

        val request = mock.requests.single()
        assertNull(request.headers[HttpHeaders.Authorization])
        assertNull(request.headers["tenantCode"])
    }

    @Test
    fun bodyIsSentAndReceivedAsJson() = runTest {
        val mock = MockAknClient(responseJson = """{"name": "back"}""")

        val response: Echo = mock.client.post("x") { setBody(Echo(name = "sent")) }.body()

        val sent = mock.requests.single().body
        assertEquals("""{"name":"sent"}""", sent.toByteArray().decodeToString())
        assertEquals("application/json", sent.contentType?.withoutParameters().toString())
        assertEquals(Echo(name = "back"), response)
    }

    @Test
    fun non2xxResponsesThrow() = runTest {
        for (status in listOf(HttpStatusCode.Unauthorized, HttpStatusCode.InternalServerError)) {
            val mock = MockAknClient(status = status)
            val error = assertFailsWith<ResponseException> { mock.client.get("x") }
            assertEquals(status, error.response.status)
        }
    }
}
