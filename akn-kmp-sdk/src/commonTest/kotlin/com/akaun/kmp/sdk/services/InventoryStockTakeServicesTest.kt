package com.akaun.kmp.sdk.services

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.INVENTORY_ITEMS_JSON
import com.akaun.kmp.sdk.fixtures.SERIAL_NUMBERS_JSON
import com.akaun.kmp.sdk.fixtures.SERIAL_VALIDATION_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_DEVICES_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_ITEM_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_OFFLINE_DEVICE_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_OFFLINE_ITEMS_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_RECORDS_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_RECORD_INV_ITEM_DEVICE_JSON
import com.akaun.kmp.sdk.fixtures.STOCK_TAKE_SESSIONS_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionHeaderModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordModel
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_device
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices.InvItemHdrService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices.InvItemService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.serialnumberservices.SerialNumberService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeDeviceService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeOfflineService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeRecordService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeSessionService
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.SerialNumberValidationDto
import com.akaun.kmp.sdk.support.MockAknClient
import com.akaun.kmp.sdk.support.MockAknClient.Companion.BASE_URL
import com.akaun.kmp.sdk.support.bodyText
import com.akaun.kmp.sdk.utils.wrapper.ApiResult
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InventoryStockTakeServicesTest {

    private fun ok(data: String) = """{"code":"OK_RESPONSE","message":"","data":$data}"""
    private fun paged(fixture: String) =
        """{"totalRecords":0,"offset":0,"limit":20,"code":"OK_RESPONSE","message":"","data":${AknJson.parseToJsonElement(fixture).jsonObject["data"]}}"""
    private fun firstOf(fixture: String) = ok((AknJson.parseToJsonElement(fixture).jsonObject["data"] as JsonArray)[0].toString())
    private fun HttpRequestData.line() = "${method.value} ${url.toString().removePrefix(BASE_URL)}"

    private val record = AknJson.decodeFromString<PagingResponseModel<StockTakeSessionRecordModel>>(STOCK_TAKE_RECORDS_JSON).data.first()
    private val device = AknJson.decodeFromString<ApiResponseModel<StockTakeDeviceModel>>(STOCK_TAKE_DEVICES_JSON).data!!.first()
    private val session = AknJson.decodeFromString<ApiResponseModel<StockTakeSessionHeaderModel>>(STOCK_TAKE_SESSIONS_JSON).data!!.first()
    private val sessionDevice = StockTakeSessionDeviceModel(bl_inv_stock_take_session_device(guid = "sd1"))

    @Test
    fun inventoryItems() = runTest {
        val one = MockAknClient(responseJson = firstOf(STOCK_TAKE_ITEM_JSON))
        assertEquals("SERIAL123", InvItemService(one.client).getInventoryItemByGuid("i1").data!!.bl_inv_mst_item_hdr.code)

        val many = MockAknClient(responseJson = INVENTORY_ITEMS_JSON)
        val service = InvItemService(many.client)
        service.getInventoryItemFromItemCode("C1", "L1")
        service.getInventoryItemFromSerialOrBatch("S1", "L1")
        val byCriteria = service.getInventoryItemByCriteria(code = "aAa")
        service.getInventoryItemByNameAndCode(name = "Beef")

        assertEquals("GET core2/tnt/dm/erp/inv/inv-items/backoffice-ep/i1", one.requests.single().line())
        assertEquals(
            listOf(
                "GET core2/tnt/dm/erp/inv/inv-items/stock-take/items/backoffice-ep?item_code=C1&location_guid=L1",
                "GET core2/tnt/dm/erp/inv/inv-items/stock-take/serial-batch/backoffice-ep?scan_code=S1&location_guid=L1",
                "GET core2/tnt/dm/erp/inv/inv-items/backoffice-ep/query?calcTotalRecords=true&limit=10&offset=0&code=aAa",
                // Backend requires both name and code (empty is accepted); the old SDK omitted a null one and got HTTP 400
                "GET core2/tnt/dm/erp/inv/inv-items/search-name-code/backoffice-ep?name=Beef&code=&offset=0&limit=10",
            ),
            many.requests.map { it.line() },
        )
        assertEquals("aAa", byCriteria.data!!.first().bl_inv_mst_item_hdr.code)

        val hdrs = MockAknClient(responseJson = paged(INVENTORY_ITEMS_JSON))
        InvItemHdrService(hdrs.client).getInventoryItemHdrsByCriteria("fi1")
        assertEquals("GET core2/tnt/dm/erp/inv/inv-items/hdrs/query?guid_fi_mst_item=fi1", hdrs.requests.single().line())
    }

    @Test
    fun serialNumbers() = runTest {
        val dto = SerialNumberValidationDto(txn_type = "SALES", serialNumberList = setOf("S1"))

        val validation = MockAknClient(responseJson = SERIAL_VALIDATION_JSON)
        val output = SerialNumberService(validation.client).validateSerialNumberList(dto)
        assertEquals("POST core2/tnt/dm/inv/serial-numbers/validation", validation.requests.single().line())
        assertTrue("\"serialNumberList\":[\"S1\"]" in validation.requests.single().bodyText())
        assertEquals("INVALID", output.data!!.serialNumberList.first().status)

        val multi = MockAknClient(responseJson = ok("""{"txn_type":"SALES"}"""))
        val multiResult = SerialNumberService(multi.client).validateSerialNumbers(dto) // was Sandwich ApiResponse
        assertEquals("POST core2/tnt/dm/inv/serial-numbers/multi-validation", multi.requests.single().line())
        check(multiResult is ApiResult.Success)
        assertEquals("SALES", multiResult.data.data!!.txn_type)

        val query = MockAknClient(responseJson = SERIAL_NUMBERS_JSON)
        val page = SerialNumberService(query.client).getSerialNumbersByCriteria(sn_ids = linkedSetOf("A", "B"))
        assertEquals(
            "GET core2/tnt/dm/inv/serial-numbers/query?sn_id=&sn_ids=A&sn_ids=B&hdr_item_guid=&location_guid=&quantity_balance_greater_than=0&calcTotalRecords=true&limit=100&offset=0",
            query.requests.single().line(),
        )
        check(page is ApiResult.Success)
        assertEquals(2, page.data.data.size)
    }

    @Test
    fun stockTakeDevices() = runTest {
        val mock = MockAknClient(responseJson = ok(AknJson.encodeToString(StockTakeDeviceModel.serializer(), device)))
        val service = StockTakeDeviceService(mock.client)
        service.createStockTakeDevice(device)
        service.updateStockTakeDevice(device)
        service.getStockTakeDeviceByGuid("d1")
        assertEquals(
            listOf(
                "POST core2/tnt/dm/erp/stocktake/devices/backoffice-ep",
                "PUT core2/tnt/dm/erp/stocktake/devices/backoffice-ep",
                "GET core2/tnt/dm/erp/stocktake/devices/backoffice-ep/d1",
            ),
            mock.requests.map { it.line() },
        )

        val all = MockAknClient(responseJson = STOCK_TAKE_DEVICES_JSON)
        assertEquals("google", StockTakeDeviceService(all.client).getAllStockTakeDevice().data!!.first().bl_inv_stock_take_device.brand)
        assertEquals("GET core2/tnt/dm/erp/stocktake/devices/backoffice-ep", all.requests.single().line())

        val query = MockAknClient(responseJson = paged(STOCK_TAKE_DEVICES_JSON))
        StockTakeDeviceService(query.client).getStockTakeDeviceByCriteria(code = "RF95")
        assertEquals("GET core2/tnt/dm/erp/stocktake/devices/backoffice-ep/query?calcTotalRecords=true&code=RF95&limit=100&offset=0", query.requests.single().line())
    }

    @Test
    fun stockTakeSessionDevices() = runTest {
        val mock = MockAknClient(responseJson = ok(AknJson.encodeToString(StockTakeSessionDeviceModel.serializer(), sessionDevice)))
        val service = StockTakeDeviceService(mock.client)
        service.createStockTakeSessionDevice(sessionDevice)
        service.updateStockTakeSessionDevice(sessionDevice)
        val byGuid = service.getStockTakeSessionDeviceByGuid("sd1")
        assertEquals("sd1", byGuid.data!!.bl_inv_stock_take_session_device.guid)

        val deleted = MockAknClient(responseJson = ok("true"))
        assertEquals(true, StockTakeDeviceService(deleted.client).deleteStockTakeSessionDevice("sd1").data)

        val query = MockAknClient(responseJson = """{"totalRecords":0,"offset":0,"limit":20,"code":"OK_RESPONSE","message":"","data":[]}""")
        StockTakeDeviceService(query.client).getStockTakeSessionDeviceByCriteria(sessionGuid = "s1")

        assertEquals(
            listOf(
                "POST core2/dm/stocktake-device",
                "PUT core2/dm/stocktake-device",
                // Old SDK called .../query with an unused @Path, which Retrofit rejects; the backend route is /{guid}
                "GET core2/dm/stocktake-device/sd1",
            ),
            mock.requests.map { it.line() },
        )
        assertEquals("DELETE core2/dm/stocktake-device/sd1", deleted.requests.single().line())
        assertEquals("GET core2/dm/stocktake-device/query?calcTotalRecords=true&session_guid=s1&device_guid=&limit=100&offset=0", query.requests.single().line())
    }

    @Test
    fun stockTakeSessions() = runTest {
        val all = MockAknClient(responseJson = STOCK_TAKE_SESSIONS_JSON)
        val service = StockTakeSessionService(all.client)
        assertEquals("fsfsfsf", service.getAllStockTakeSessionHdr().data!!.first().bl_inv_stock_take_session_hdr.code)
        service.getStockTakeSessionByDeviceGuid("dev1", false)

        val single = MockAknClient(responseJson = ok(AknJson.encodeToString(StockTakeSessionHeaderModel.serializer(), session)))
        StockTakeSessionService(single.client).getStockTakeSessionHdrByGuid("s1")
        StockTakeSessionService(single.client).createStockTakeSessionHdr(session)

        val query = MockAknClient(responseJson = paged(STOCK_TAKE_SESSIONS_JSON))
        val page = StockTakeSessionService(query.client).getStockTakeSessionHdrByQuery(orderBy = "code")
        // Old SDK typed this as session-device models, so every header came back empty
        assertEquals("fsfsfsf", page.data.first().bl_inv_stock_take_session_hdr.code)

        assertEquals(
            listOf("GET core2/dm/stocktake-hdr/backoffice-ep", "GET core2/dm/stocktake-hdr/device-session/backoffice-ep/dev1/false"),
            all.requests.map { it.line() },
        )
        assertEquals(listOf("GET core2/dm/stocktake-hdr/backoffice-ep/s1", "POST core2/dm/stocktake-hdr/backoffice-ep"), single.requests.map { it.line() })
        assertEquals("GET core2/dm/stocktake-hdr/backoffice-ep/query?calcTotalRecords=true&orderBy=code&order=ASC&limit=100&offset=0", query.requests.single().line())
    }

    @Test
    fun stockTakeRecords() = runTest {
        val mock = MockAknClient(responseJson = STOCK_TAKE_RECORDS_JSON) // paging keys are ignored by ApiResponseModel
        val service = StockTakeRecordService(mock.client)
        service.createStokeTakeSessionRecord(record)
        service.createMultiStokeTakeSessionRecord(listOf(record))
        service.updateStokeTakeSessionRecord(record)
        service.updateMultiStokeTakeSessionRecord(listOf(record))
        service.getAllStokeTakeSessionRecord()
        assertEquals(
            listOf(
                "POST core2/dm/stocktake-record",
                "POST core2/dm/stocktake-record/multi",
                "PUT core2/dm/stocktake-record",
                "PUT core2/dm/stocktake-record/multi",
                "GET core2/dm/stocktake-record",
            ),
            mock.requests.map { it.line() },
        )
        // /multi takes a JSON array (backend List<...>); the old SDK sent one object and got 400
        assertTrue(mock.requests[1].bodyText().startsWith("[{"))
        assertTrue(mock.requests[3].bodyText().startsWith("[{"))
        assertTrue(mock.requests[0].bodyText().startsWith("{"))

        val single = MockAknClient(responseJson = ok(AknJson.encodeToString(StockTakeSessionRecordModel.serializer(), record)))
        assertEquals("VCMWB", StockTakeRecordService(single.client).getStokeTakeSessionRecordByGuid("r1").data!!.bl_inv_stock_take_session_record.item_code)
        val deleted = MockAknClient(responseJson = ok("true"))
        StockTakeRecordService(deleted.client).deleteStokeTakeSessionRecord("r1")
        assertEquals("GET core2/dm/stocktake-record/r1", single.requests.single().line())
        assertEquals("DELETE core2/dm/stocktake-record/r1", deleted.requests.single().line())

        val query = MockAknClient(responseJson = STOCK_TAKE_RECORDS_JSON)
        StockTakeRecordService(query.client).getStokeTakeSessionRecordByCriteria(guid_session_hdr = "s1")
        val withItems = MockAknClient(responseJson = STOCK_TAKE_RECORD_INV_ITEM_DEVICE_JSON)
        val rows = StockTakeRecordService(withItems.client).getStockTakeRecordWithInvItemByCriteria(guid_session_device = "sd1")
        assertEquals("GET core2/dm/stocktake-record/query?guid_session_hdr=s1&guid_session_device=&calcTotalRecords=true&limit=100&offset=0", query.requests.single().line())
        assertEquals("GET core2/dm/stocktake-record/inv-item-devices/query?guid_session_hdr=&guid_session_device=sd1&calcTotalRecords=true&limit=100&offset=0", withItems.requests.single().line())
        assertEquals("RF95", rows.data.single().bl_inv_stock_take_device.code)
    }

    @Test
    fun stockTakeRecordLookups() = runTest {
        val items = MockAknClient(responseJson = STOCK_TAKE_ITEM_JSON)
        val service = StockTakeRecordService(items.client)
        service.getInventoryItemFromItemCode("C1", "L1")
        service.getInventoryItemFromSerialOrBatch("S1", "L1")
        assertEquals(
            listOf(
                "GET core2/tnt/dm/erp/inv/inv-items/stock-take/items/backoffice-ep?item_code=C1&location_guid=L1",
                "GET core2/tnt/dm/erp/inv/inv-items/stock-take/serial-batch/backoffice-ep?scan_code=S1&location_guid=L1",
            ),
            items.requests.map { it.line() },
        )

        val serials = MockAknClient(responseJson = SERIAL_NUMBERS_JSON)
        val result = StockTakeRecordService(serials.client).getSerialNumbersByCriteria(sn_id = "S1")
        check(result is ApiResult.Success)
        assertTrue(serials.requests.single().line().startsWith("GET core2/tnt/dm/inv/serial-numbers/query?sn_id=S1&"))
    }

    @Test
    fun offline() = runTest {
        val info = MockAknClient(responseJson = STOCK_TAKE_OFFLINE_DEVICE_JSON)
        assertEquals("RF95", StockTakeOfflineService(info.client).getAllOfflineStockTakeInfoForDevice("RF95").data!!.bl_inv_stock_take_device.code)
        assertEquals("GET core2/tnt/dm/erp/stocktake/offline/device-stocktake-info/RF95", info.requests.single().line())

        val items = MockAknClient(responseJson = STOCK_TAKE_OFFLINE_ITEMS_JSON)
        assertEquals("KEN1000009R", StockTakeOfflineService(items.client).getInventoryItemsForOffline().data!!.first().bl_inv_mst_item_hdr.code)
        assertEquals("GET core2/tnt/dm/erp/stocktake/offline/inv-items", items.requests.single().line())

        val multi = MockAknClient(responseJson = ok("[]"))
        StockTakeOfflineService(multi.client).createMultiStokeTakeSessionRecord(listOf(record))
        assertEquals("POST core2/dm/stocktake-record/multi", multi.requests.single().line())
        assertTrue(multi.requests.single().bodyText().startsWith("[{"))
    }
}
