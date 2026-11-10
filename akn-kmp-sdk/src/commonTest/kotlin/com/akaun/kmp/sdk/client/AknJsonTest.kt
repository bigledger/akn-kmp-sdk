package com.akaun.kmp.sdk.client

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

// Mirrors the shape of the real models: snake_case names, nullable fields, defaulted lists.
@Serializable
private data class SampleRow(
    val amount_discount: String? = null,
    val lines: List<String> = emptyList(),
)

class AknJsonTest {

    @Test
    fun backendBigDecimalNumberDecodesIntoStringField() {
        // Backend sends BigDecimal as a JSON number; old Kotlin models declare some of these as String?
        val row = AknJson.decodeFromString<SampleRow>("""{"amount_discount": 12.50}""")
        assertEquals("12.50", row.amount_discount)
    }

    @Test
    fun nullForDefaultedFieldFallsBackToDefault() {
        val row = AknJson.decodeFromString<SampleRow>("""{"lines": null}""")
        assertEquals(emptyList(), row.lines)
    }

    @Test
    fun unknownKeysAreIgnored() {
        val row = AknJson.decodeFromString<SampleRow>("""{"amount_discount": "1", "not_in_model": {"x": 1}}""")
        assertEquals(SampleRow(amount_discount = "1"), row)
    }

    @Test
    fun requestBodyOmitsNullsButKeepsNonNullDefaults() {
        val json = AknJson.encodeToString(SampleRow.serializer(), SampleRow(amount_discount = null, lines = listOf("a")))
        assertEquals("""{"lines":["a"]}""", json)
    }
}
