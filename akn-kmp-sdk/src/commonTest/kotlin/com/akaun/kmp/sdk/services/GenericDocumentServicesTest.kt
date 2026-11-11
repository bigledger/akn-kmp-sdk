package com.akaun.kmp.sdk.services

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.GENERIC_DOCUMENT_INVOICE_JSON
import com.akaun.kmp.sdk.fixtures.GENERIC_DOCUMENT_LINK_JSON
import com.akaun.kmp.sdk.fixtures.GENERIC_DOCUMENT_SNAPSHOT_JSON
import com.akaun.kmp.sdk.fixtures.HDR_IMPORT_JSON
import com.akaun.kmp.sdk.fixtures.INVENTORY_ITEMS_JSON
import com.akaun.kmp.sdk.fixtures.QTY_DISCREPANCY_JSON
import com.akaun.kmp.sdk.fixtures.SCAN_LINES_JSON
import com.akaun.kmp.sdk.fixtures.SERIAL_NUMBERS_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_ITEM_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentLinkModel
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentModel
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentScanLineModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices.BaseGenDocTypeService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices.GenericDocLinkService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices.GenericDocumentHdrImportService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.scanservices.GenDocScanLineService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.GenericDocumentDto
import com.akaun.kmp.sdk.support.MockAknClient
import com.akaun.kmp.sdk.support.MockAknClient.Companion.BASE_URL
import com.akaun.kmp.sdk.support.bodyText
import com.akaun.kmp.sdk.utils.wrapper.ApiResult
import com.akaun.kmp.sdk.utils.wrapper.apiResultOf
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertFailsWith
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.jsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class GenericDocumentServicesTest {

    private val invoice = AknJson.decodeFromString<BasicApiResponseModel<GenericDocumentModel>>(GENERIC_DOCUMENT_INVOICE_JSON).data!!

    /** Re-wraps a list fixture (ApiResponseModel) as a PagingResponseModel response. */
    private fun paged(fixture: String): String {
        val data = AknJson.parseToJsonElement(fixture).jsonObject["data"]
        return """{"totalRecords":0,"offset":0,"limit":20,"code":"OK_RESPONSE","message":"","data":$data}"""
    }

    @Test
    fun genericDocumentCrudUsesDocTypeInPath() = runTest {
        val mock = MockAknClient(responseJson = GENERIC_DOCUMENT_INVOICE_JSON)
        val service = BaseGenDocTypeService(mock.client)

        service.create("internal-sales-invoices", invoice)
        val byGuid = service.getGenericDocumentByGuid("internal-sales-invoices", "0010fd1d-0662-0eb1-9fe5-3aa5236f5712")
        service.updateGenericDocument("internal-sales-invoices", invoice)

        assertEquals(listOf(HttpMethod.Post, HttpMethod.Get, HttpMethod.Put), mock.requests.map { it.method })
        assertEquals(
            listOf(
                "core2/tnt/dm/erp/gen-doc/internal-sales-invoices",
                "core2/tnt/dm/erp/gen-doc/internal-sales-invoices/0010fd1d-0662-0eb1-9fe5-3aa5236f5712",
                "core2/tnt/dm/erp/gen-doc/internal-sales-invoices",
            ),
            mock.requests.map { it.url.toString().removePrefix(BASE_URL) },
        )
        assertTrue("\"guid\":\"0010fd1d-0662-0eb1-9fe5-3aa5236f5712\"" in mock.requests[0].bodyText())
        assertEquals("INTERNAL_SALES_INVOICE", byGuid.data!!.bl_fi_generic_doc_hdr.server_doc_type)
    }

    @Test
    fun snapshotQuery() = runTest {
        val mock = MockAknClient(responseJson = GENERIC_DOCUMENT_SNAPSHOT_JSON)

        val page = BaseGenDocTypeService(mock.client).getGenericDocumentByDTOSnapshot(
            "internal-stock-adjustment", GenericDocumentDto(limit = 10, order = "DESC", orderBy = "date_txn"),
        )

        assertEquals("${BASE_URL}core2/tnt/dm/erp/gen-doc/internal-stock-adjustment/custom/query/snapshot/backoffice-ep", mock.requests.single().url.toString())
        assertTrue("\"orderBy\":\"date_txn\"" in mock.requests.single().bodyText())
        assertEquals("11da6728-8dd8-4b40-ae5b-baa471c1639d", page.data.single().bl_fi_generic_doc_hdr.guid)
    }

    @Test
    fun createLinksPostsAJsonArray() = runTest {
        val link = AknJson.decodeFromString<ApiResponseModel<GenericDocumentLinkModel>>(GENERIC_DOCUMENT_LINK_JSON).data!!.single()
        val mock = MockAknClient(responseJson = GENERIC_DOCUMENT_LINK_JSON)

        val created = GenericDocLinkService(mock.client).createGenericDocumentLinkBackofficeEP(listOf(link))

        assertEquals("${BASE_URL}core2/tnt/dm/erp/generic-doc/links/backoffice-ep", mock.requests.single().url.toString())
        assertTrue(mock.requests.single().bodyText().startsWith("[{\"bl_fi_generic_doc_link\":"))
        assertEquals("0e4df252-8242-4d9e-9461-b8e74f7c89c7", created.data!!.single().bl_fi_generic_doc_link.guid)
    }

    @Test
    fun hdrImportQuerySendsGuidsAsRepeatedKeys() = runTest {
        val mock = MockAknClient(responseJson = HDR_IMPORT_JSON)

        val page = GenericDocumentHdrImportService(mock.client).getGenericDocumentHdrLineLinkOpenQueueByCriteria(
            "internal-sales-orders", "INTERNAL_SALES_ORDER", null, 0, 30, linkedSetOf("g1", "g2"),
        )

        assertEquals(
            "${BASE_URL}core2/tnt/dm/erp/gen-doc/hdr-imports/internal-sales-orders/query" +
                "?line_open_queue_server_doc_type_1=INTERNAL_SALES_ORDER&offset=0&limit=30&guids=g1&guids=g2",
            mock.requests.single().url.toString(),
        )
        assertEquals("0005ed55-23fe-9d34-4918-42349187d83e", page.data.single().bl_fi_generic_doc_hdr.guid)
    }

    @Test
    fun scanLineWrites() = runTest {
        val line = AknJson.decodeFromString<PagingResponseModel<GenericDocumentScanLineModel>>(SCAN_LINES_JSON).data.first()
        val single = """{"code":"OK_RESPONSE","message":"","data":${AknJson.encodeToString(GenericDocumentScanLineModel.serializer(), line)}}"""
        val mock = MockAknClient(responseJson = single)
        val service = GenDocScanLineService(mock.client)

        service.createGenDocScanLine(line)
        service.updateGenDocScanLine(line)
        service.getByGuidGenDocScanLine("s1")

        assertEquals(listOf(HttpMethod.Post, HttpMethod.Put, HttpMethod.Get), mock.requests.map { it.method })
        assertEquals(
            listOf("scan-lines/backoffice-ep", "scan-lines/backoffice-ep", "scan-lines/backoffice-ep/s1"),
            mock.requests.map { it.url.toString().substringAfter("generic-doc/") },
        )

        val multi = MockAknClient(responseJson = paged(SCAN_LINES_JSON).replace("\"totalRecords\":0,\"offset\":0,\"limit\":20,", ""))
        val created = GenDocScanLineService(multi.client).createMultiGenDocScanLine(listOf(line))
        assertEquals("${BASE_URL}core2/tnt/dm/erp/generic-doc/scan-lines/multi/backoffice-ep", multi.requests.single().url.toString())
        assertTrue(multi.requests.single().bodyText().startsWith("[{"))
        assertEquals(2, created.data!!.size)

        val delete = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":true}""")
        assertEquals(true, GenDocScanLineService(delete.client).deleteGenDocScanLine("s1").data)
        assertEquals(HttpMethod.Delete, delete.requests.single().method)
        assertEquals("${BASE_URL}core2/tnt/dm/erp/generic-doc/scan-lines/backoffice-ep/s1", delete.requests.single().url.toString())
    }

    @Test
    fun scanLineReads() = runTest {
        val all = MockAknClient(responseJson = paged(SCAN_LINES_JSON).replace("\"totalRecords\":0,\"offset\":0,\"limit\":20,", ""))
        GenDocScanLineService(all.client).getAllGenDocScanLine(item_code = "SERIAL123")
        // Retrofit sent default "" values as empty params; so does Ktor
        assertEquals("${BASE_URL}core2/tnt/dm/erp/generic-doc/scan-lines/backoffice-ep?item_code=SERIAL123&location_guid=", all.requests.single().url.toString())

        val query = MockAknClient(responseJson = SCAN_LINES_JSON)
        val page = GenDocScanLineService(query.client).getByCriteriaGenDocScanLine(generic_doc_hdr_guid = "h1", location_guid = "b1")
        assertEquals("${BASE_URL}core2/tnt/dm/erp/generic-doc/scan-lines/backoffice-ep/query?generic_doc_hdr_guid=h1&guid_branch=b1", query.requests.single().url.toString())
        assertEquals("asdwqte", page.data[1].bl_fi_generic_doc_scan_line.serial_number)

        val qty = MockAknClient(responseJson = QTY_DISCREPANCY_JSON)
        val discrepancy = GenDocScanLineService(qty.client).getQtyDiscrepancyByHdrGuid("h1")
        assertEquals("${BASE_URL}core2/tnt/dm/erp/generic-doc/scan-lines/backoffice-ep/qty-discrepancy/h1", qty.requests.single().url.toString())
        assertEquals("-1", discrepancy.data!!.single().qty_discrepancy)
    }

    @Test
    fun inventoryLookupsFromScanService() = runTest {
        val byGuid = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":${AknJson.parseToJsonElement(STOCK_TAKE_ITEM_JSON).jsonObject["data"]!!.let { (it as kotlinx.serialization.json.JsonArray)[0] }}}""")
        assertEquals("SERIAL123", GenDocScanLineService(byGuid.client).getInventoryItemByGuid("i1").data!!.bl_inv_mst_item_hdr.code)
        assertEquals("${BASE_URL}core2/tnt/dm/erp/inv/inv-items/backoffice-ep/i1", byGuid.requests.single().url.toString())

        val items = MockAknClient(responseJson = STOCK_TAKE_ITEM_JSON)
        val service = GenDocScanLineService(items.client)
        service.getInventoryItemFromItemCode("SERIAL123", "loc1")
        service.getInventoryItemFromSerialOrBatch("SN-1", "loc1")
        assertEquals(
            listOf(
                "inv-items/stock-take/items/backoffice-ep?item_code=SERIAL123&location_guid=loc1",
                "inv-items/stock-take/serial-batch/backoffice-ep?scan_code=SN-1&location_guid=loc1",
            ),
            items.requests.map { it.url.toString().substringAfter("erp/inv/") },
        )
    }

    @Test
    fun serialNumbersQueryReturnsApiResult() = runTest {
        val ok = MockAknClient(responseJson = SERIAL_NUMBERS_JSON)
        val success = GenDocScanLineService(ok.client).getSerialNumbersByCriteria(sn_ids = linkedSetOf("A", "B"), hdr_item_guid = "i1")
        assertEquals(
            "${BASE_URL}core2/tnt/dm/inv/serial-numbers/query" +
                "?sn_id=&sn_ids=A&sn_ids=B&hdr_item_guid=i1&location_guid=&quantity_balance_greater_than=0&calcTotalRecords=true&limit=100&offset=0",
            ok.requests.single().url.toString(),
        )
        check(success is ApiResult.Success)
        assertEquals("6FCCF26B-EB82-4692-B4C2-84C758E05CC1", success.data.data.first().bl_inv_serial_number_hdr.sn_id)

        val failing = MockAknClient(responseJson = """{"code":"SERVER_ERROR","message":"boom","data":null}""", status = HttpStatusCode.InternalServerError)
        val error = assertIs<ApiResult.Error<*>>(GenDocScanLineService(failing.client).getSerialNumbersByCriteria())
        assertEquals(500, error.code)
        assertTrue("boom" in error.message!!)

        val broken = MockAknClient(responseJson = "not json")
        assertIs<ApiResult.Exception<*>>(GenDocScanLineService(broken.client).getSerialNumbersByCriteria())
    }

    @Test
    fun apiResultNeverSwallowsCoroutineCancellation() = runTest {
        assertFailsWith<CancellationException> { apiResultOf<String> { throw CancellationException("cancelled") } }
    }
}
