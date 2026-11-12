package com.akaun.kmp.sdk.models

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.GENERIC_DOCUMENT_INVOICE_JSON
import com.akaun.kmp.sdk.fixtures.GENERIC_DOCUMENT_LINK_JSON
import com.akaun.kmp.sdk.fixtures.GENERIC_DOCUMENT_SNAPSHOT_JSON
import com.akaun.kmp.sdk.fixtures.HDR_IMPORT_JSON
import com.akaun.kmp.sdk.fixtures.QTY_DISCREPANCY_JSON
import com.akaun.kmp.sdk.fixtures.SCAN_LINES_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentHdrLineLinkQueueListModel
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentLinkModel
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentModel
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentScanLineItemQtyContainer
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentScanLineModel
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_line
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.GenericDocumentDto
import kotlinx.serialization.json.JsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Instant

class GenericDocumentModelsTest {

    @Test
    fun documentWithBinAndBatchLinesDecodes() {
        val doc = AknJson.decodeFromString<PagingResponseModel<GenericDocumentModel>>(GENERIC_DOCUMENT_SNAPSHOT_JSON).data.single()

        assertEquals("11da6728-8dd8-4b40-ae5b-baa471c1639d", doc.bl_fi_generic_doc_hdr.guid)
        assertEquals("INTERNAL_STOCK_ADJUSTMENT", doc.bl_fi_generic_doc_hdr.server_doc_type)
        val (binLine, batchLine) = doc.bl_fi_generic_doc_line
        assertEquals("5555", binLine.bin_no!!.bins!!.first().bin_hdr_code)
        assertEquals(50, binLine.bin_no!!.bins!!.first().qty)
        val batch = batchLine.batch_no!!.batches!!.single()
        assertEquals("5555", batch.batch_no)
        assertEquals("2022-12-30T16:00:00.000Z", batch.expiry_date)
        assertEquals("pcs", binLine.item_property_json!!.uom)
    }

    @Test
    fun singleInvoiceDecodes() {
        val doc = AknJson.decodeFromString<BasicApiResponseModel<GenericDocumentModel>>(GENERIC_DOCUMENT_INVOICE_JSON).data!!

        assertEquals("0010fd1d-0662-0eb1-9fe5-3aa5236f5712", doc.bl_fi_generic_doc_hdr.guid)
        assertEquals("INTERNAL_SALES_INVOICE", doc.bl_fi_generic_doc_hdr.server_doc_type)
        val props = doc.bl_fi_generic_doc_line.single().item_property_json!!
        assertEquals("ITEM ABC", props.itemCode)
        assertEquals("PCS", props.uom)
    }

    @Test
    fun documentColumnsDecodeToBackendTypes() {
        val doc = AknJson.decodeFromString<BasicApiResponseModel<GenericDocumentModel>>(GENERIC_DOCUMENT_INVOICE_JSON).data!!
        val hdr = doc.bl_fi_generic_doc_hdr
        val line = doc.bl_fi_generic_doc_line.single()

        // ZonedDateTime -> Instant, Map -> JsonObject, BigDecimal -> Double (issue #12)
        assertEquals(Instant.parse("2023-04-04T15:00:34.347Z"), hdr.date_txn)
        assertEquals(Instant.parse("2023-04-04T15:04:10.265771Z"), line.created_date)
        assertTrue("billingInfo" in hdr.billing_json!!)
        assertEquals(0.0, line.cost_wa_amount)
        assertEquals(JsonObject(emptyMap()), line.entity_json)

        // A decoded document sent back keeps its values (what updateGenericDocument relies on)
        val back = AknJson.decodeFromString(GenericDocumentModel.serializer(), AknJson.encodeToString(GenericDocumentModel.serializer(), doc))
        assertEquals(doc, back)
    }

    @Test
    fun booleanIntArrayAndEnumColumnsDecode() {
        // These columns are null in the committed fixtures, so use the shapes staging sends
        val hdr = AknJson.decodeFromString<bl_fi_generic_doc_hdr>(
            """{"foreign_ccy":true,"doc_level":2,"status_json":[{"status":"POSTED"}],"delivery_option":{}}""",
        )
        assertEquals(true, hdr.foreign_ccy)
        assertEquals(2, hdr.doc_level)
        assertEquals(1, hdr.status_json!!.size)
        assertEquals(JsonObject(emptyMap()), hdr.delivery_option)
        // Backend enum PointTypeEnum arrives as its name
        assertEquals("REDEEM", AknJson.decodeFromString<bl_fi_generic_doc_line>("""{"point_type":"REDEEM"}""").point_type)
    }

    @Test
    fun lineAcceptsEmptySerialNoAndNumbersSentAsStrings() {
        // Real staging shapes: 19 lines send serial_no {} and 579 values arrive as numeric strings
        val line = AknJson.decodeFromString<bl_fi_generic_doc_line>(
            """{"guid":"l1","serial_no":{},"item_property_json":{"qty":"3","qtyUom":"1.00","unitPriceNetUom":"10.00","unitPriceStdUomWithTax":"0.00"}}""",
        )

        assertEquals(emptyList(), line.serial_no!!.serialNumbers)
        assertEquals(3.0, line.item_property_json!!.qty)
        assertEquals(10.0, line.item_property_json!!.unitPriceNetUom)
    }

    @Test
    fun scanLinesHdrImportDiscrepancyAndLinkDecode() {
        val scan = AknJson.decodeFromString<PagingResponseModel<GenericDocumentScanLineModel>>(SCAN_LINES_JSON).data
        assertEquals("asdwqte", scan[1].bl_fi_generic_doc_scan_line.serial_number)

        val import = AknJson.decodeFromString<PagingResponseModel<GenericDocumentHdrLineLinkQueueListModel>>(HDR_IMPORT_JSON).data.single()
        assertEquals("0005ed55-23fe-9d34-4918-42349187d83e", import.bl_fi_generic_doc_hdr.guid)
        assertEquals("ef48a541-8229-425a-a187-f669ec19b37b", import.bl_fi_generic_doc_line_open_queues.single().guid)

        val qty = AknJson.decodeFromString<ApiResponseModel<GenericDocumentScanLineItemQtyContainer>>(QTY_DISCREPANCY_JSON).data!!.single()
        assertEquals("SERIAL888", qty.item_code)
        assertEquals("-1", qty.qty_discrepancy) // backend sends the number -1 into a String? field

        val link = AknJson.decodeFromString<ApiResponseModel<GenericDocumentLinkModel>>(GENERIC_DOCUMENT_LINK_JSON).data!!.single()
        assertEquals("0e4df252-8242-4d9e-9461-b8e74f7c89c7", link.bl_fi_generic_doc_link.guid)
    }

    @Test
    fun snapshotQueryBodyKeepsBackendFieldNames() {
        val body = AknJson.encodeToString(GenericDocumentDto.serializer(), GenericDocumentDto(guids = setOf("g1"), limit = 10, order = "DESC", orderBy = "date_txn"))

        assertTrue("\"guids\":[\"g1\"]" in body, body)
        assertTrue("\"orderBy\":\"date_txn\"" in body, body) // backend @JsonProperty("orderBy")
    }
}
