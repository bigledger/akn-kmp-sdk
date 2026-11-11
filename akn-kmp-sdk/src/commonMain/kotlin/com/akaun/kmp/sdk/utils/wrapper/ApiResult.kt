package com.akaun.kmp.sdk.utils.wrapper

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlin.coroutines.cancellation.CancellationException

sealed class ApiResult<T : Any> {
    class Success<T: Any>(val data: T) : ApiResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : ApiResult<T>()
    class Exception<T: Any>(val e: Throwable) : ApiResult<T>()
}

/**
 * Runs [call] and wraps the outcome, replacing the old SDK's Sandwich ApiResponse:
 * non-2xx -> [ApiResult.Error] (status + body text), any other failure -> [ApiResult.Exception].
 */
internal suspend fun <T : Any> apiResultOf(call: suspend () -> T): ApiResult<T> =
    try {
        ApiResult.Success(call())
    } catch (e: CancellationException) {
        throw e // never swallow coroutine cancellation
    } catch (e: ResponseException) {
        ApiResult.Error(e.response.status.value, runCatching { e.response.bodyAsText() }.getOrNull())
    } catch (e: Throwable) {
        ApiResult.Exception(e)
    }
