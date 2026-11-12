package com.akaun.kmp.sdk.models

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
import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.InventoryItemModel
import com.akaun.kmp.sdk.models.aggregates.erp.inventoryitem.SerialNumberModel
import com.akaun.kmp.sdk.models.aggregates.erp.serialnumber.SerialNumberStatus
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionHeaderModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordInvItemDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordModel
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_hdr
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.InventoryItemOfflineDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.SerialNumberValidationDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.SerialNumberValidationOutputDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.StockTakeMobileOfflineDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Instant

class InventoryStockTakeModelsTest {

    @Test
    fun inventoryItemsDecodeWithExts() {
        val items = AknJson.decodeFromString<PagingResponseModel<InventoryItemModel>>(INVENTORY_ITEMS_JSON).data
        assertEquals("aAa", items.first().bl_inv_mst_item_hdr.code)
        assertEquals("ITEM_STATUS", items.first().bl_inv_mst_item_exts.first().param_code)

        val stockTakeItem = AknJson.decodeFromString<ApiResponseModel<InventoryItemModel>>(STOCK_TAKE_ITEM_JSON).data!!.single()
        assertEquals("SERIAL123", stockTakeItem.bl_inv_mst_item_hdr.code)
        assertEquals("SERIALIZED", stockTakeItem.bl_inv_mst_item_exts.first().param_code)
    }

    @Test
    fun serialNumbersDecode() {
        val serial = AknJson.decodeFromString<PagingResponseModel<SerialNumberModel>>(SERIAL_NUMBERS_JSON).data.first()
        assertEquals("6FCCF26B-EB82-4692-B4C2-84C758E05CC1", serial.bl_inv_serial_number_hdr.sn_id)
        assertEquals("ACTIVE", serial.bl_inv_serial_number_hdr.status)
    }

    @Test
    fun serialNumberChildListsUseWireNames() {
        // Backend sends ...lineList/extList/eventList; the old SDK's ...lines/exts/events never matched
        val serial = AknJson.decodeFromString<SerialNumberModel>(
            """{"bl_inv_serial_number_hdr":{"guid":"h1"},
               "bl_inv_serial_number_lineList":[{"guid":"l1"}],
               "bl_inv_serial_number_extList":[{"guid":"x1"}],
               "bl_inv_serial_number_eventList":[{"guid":"e1"},{"guid":"e2"}]}""",
        )
        assertEquals("l1", serial.bl_inv_serial_number_lineList.single().guid)
        assertEquals("x1", serial.bl_inv_serial_number_extList.single().guid)
        assertEquals(2, serial.bl_inv_serial_number_eventList.size)
    }

    @Test
    fun serialValidationRoundTrip() {
        val output = AknJson.decodeFromString<BasicApiResponseModel<SerialNumberValidationOutputDto>>(SERIAL_VALIDATION_JSON).data!!
        assertEquals(listOf("SERIAL123-1132", "DOES-NOT-EXIST-1"), output.serialNumberList.map { it.sn_id })
        assertEquals("INVALID", output.serialNumberList.first().status)

        val body = AknJson.encodeToString(
            SerialNumberValidationDto.serializer(),
            SerialNumberValidationDto(txn_type = "SALES", serialNumberList = setOf("S1"), serialNumbers = setOf(SerialNumberStatus(sn_id = "S1"))),
        )
        assertTrue("\"serialNumberList\":[\"S1\"]" in body, body) // backend field names are camelCase here
        assertTrue("\"serialNumbers\":[{\"sn_id\":\"S1\"}]" in body, body)
    }

    @Test
    fun stockTakeDevicesSessionsAndRecordsDecode() {
        val device = AknJson.decodeFromString<ApiResponseModel<StockTakeDeviceModel>>(STOCK_TAKE_DEVICES_JSON).data!!.first()
        assertEquals("DEVICE-002", device.bl_inv_stock_take_device.code)
        assertEquals("google", device.bl_inv_stock_take_device.brand)

        val session = AknJson.decodeFromString<ApiResponseModel<StockTakeSessionHeaderModel>>(STOCK_TAKE_SESSIONS_JSON).data!!.first()
        assertEquals("fsfsfsf", session.bl_inv_stock_take_session_hdr.code)
        assertEquals("CLOSED", session.bl_inv_stock_take_session_hdr.status)

        val record = AknJson.decodeFromString<PagingResponseModel<StockTakeSessionRecordModel>>(STOCK_TAKE_RECORDS_JSON).data.first()
        assertEquals("VCMWB", record.bl_inv_stock_take_session_record.item_code)
        assertEquals(1.0, record.bl_inv_stock_take_session_record.qty)

        val composed = AknJson.decodeFromString<PagingResponseModel<StockTakeSessionRecordInvItemDeviceModel>>(STOCK_TAKE_RECORD_INV_ITEM_DEVICE_JSON).data.single()
        assertEquals("CUTLERY SET", composed.bl_inv_stock_take_session_record.item_code)
        assertEquals("RF95", composed.bl_inv_stock_take_device.code)
        assertEquals("SERIAL123", composed.bl_inv_mst_item_hdr.code)
    }

    @Test
    fun stockTakeAndSerialColumnsDecodeToBackendTypes() {
        val session = AknJson.decodeFromString<ApiResponseModel<StockTakeSessionHeaderModel>>(STOCK_TAKE_SESSIONS_JSON).data!!.first()
        assertEquals(Instant.parse("2023-08-21T09:39:41.072003Z"), session.bl_inv_stock_take_session_hdr.date_created)
        val record = AknJson.decodeFromString<PagingResponseModel<StockTakeSessionRecordModel>>(STOCK_TAKE_RECORDS_JSON).data.first()
        assertEquals(Instant.parse("2023-06-14T05:45:46.493874Z"), record.bl_inv_stock_take_session_record.date_created)
        assertEquals(setOf("line_guid"), record.bl_inv_stock_take_session_record.input_json!!.keys)
        val serial = AknJson.decodeFromString<PagingResponseModel<SerialNumberModel>>(SERIAL_NUMBERS_JSON).data[1].bl_inv_serial_number_hdr
        assertEquals("53355/CEAGDF4ND05855", serial.sn_id)
        assertEquals(1.0, serial.quantity_base)

        // date_start used to default to "" and was sent as "date_start":""; an unset date is now left out
        val body = AknJson.encodeToString(bl_inv_stock_take_session_hdr.serializer(), bl_inv_stock_take_session_hdr(code = "S1"))
        assertEquals("""{"code":"S1"}""", body)
    }

    @Test
    fun offlineDtosDecode() {
        val items = AknJson.decodeFromString<ApiResponseModel<InventoryItemOfflineDto>>(STOCK_TAKE_OFFLINE_ITEMS_JSON).data!!
        assertEquals("KEN1000009R", items.first().bl_inv_mst_item_hdr.code)

        val info = AknJson.decodeFromString<BasicApiResponseModel<StockTakeMobileOfflineDto>>(STOCK_TAKE_OFFLINE_DEVICE_JSON).data!!
        assertEquals("RF95", info.bl_inv_stock_take_device.code)
        assertEquals("testuom", info.bl_inv_stock_take_session_hdrs.first().code)
        assertEquals(2, info.bl_inv_stock_take_session_devices.size)
    }
}
