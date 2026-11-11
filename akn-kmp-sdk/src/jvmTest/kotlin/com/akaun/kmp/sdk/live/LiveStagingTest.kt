package com.akaun.kmp.sdk.live

import com.akaun.kmp.sdk.client.createAknHttpClient
import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobAttachmentModel
import com.akaun.kmp.sdk.models.aggregates.erp.gendoc.GenericDocumentScanLineModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionDeviceModel
import com.akaun.kmp.sdk.models.aggregates.erp.stock.StockTakeSessionRecordModel
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_attachment
import com.akaun.kmp.sdk.models.dbschema.bl_fi_generic_doc_scan_line
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_device
import com.akaun.kmp.sdk.models.dbschema.bl_inv_stock_take_session_record
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.apppletloginsubjectservices.AppletLoginSubjectLinkService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.companyservices.CompanyService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.DelJobService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.JobAttachmentService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.JobDocHdrLinkService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices.BaseGenDocTypeService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.genericdocumentservices.GenericDocumentHdrImportService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.idservices.LoginService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices.InvItemHdrService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.invitemservices.InvItemService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.locationservices.LocationService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.scanservices.GenDocScanLineService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.serialnumberservices.SerialNumberService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.FileUpload
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeDeviceService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeOfflineService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeRecordService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.stockservices.StockTakeSessionService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.tripservices.TripService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.userprofileservices.UserProfileService
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.DriverTripQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.GenericDocumentDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.SerialNumberValidationDto
import com.akaun.kmp.sdk.models.aggregates.erp.serialnumber.SerialNumberStatus
import com.akaun.kmp.sdk.utils.wrapper.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Opt-in tests against the real staging backend (issue #10). They do nothing unless AKN_LIVE=1 and
 * local.properties holds akn.authToken, so CI and normal runs are unaffected.
 * Run: AKN_LIVE=1 ./gradlew :akn-kmp-sdk:jvmTest --tests '*LiveStagingTest*'
 * Round-trip tests write to staging_tenant and delete what they created in `finally`.
 */
class LiveStagingTest {

    private val props = Properties().apply {
        File("../local.properties").takeIf { it.exists() }?.reader()?.use { load(it) }
    }
    private val token = props.getProperty("akn.authToken")
    private val enabled = System.getenv("AKN_LIVE") == "1" && !token.isNullOrBlank()
    private val tenantCode = props.getProperty("akn.tenantCode", "staging_tenant")
    private val baseUrl = props.getProperty("akn.baseUrl", "https://api.akaun.cloud/")

    private fun live(block: suspend (HttpClient) -> Unit) {
        if (!enabled) return println("LiveStagingTest skipped: set AKN_LIVE=1 and akn.authToken")
        check(tenantCode == "staging_tenant") { "live tests only run on staging_tenant, got $tenantCode" }
        runBlocking { createAknHttpClient(baseUrl, token, tenantCode).use { block(it) } }
    }

    private fun log(msg: String) = println("[live] $msg")

    // ---------- A. read-only ----------

    @Test
    fun identity() = live { c ->
        val links = LoginService(c).getUserAppletLInks()
        assertEquals("OK_RESPONSE", links.code)
        assertTrue(links.data.orEmpty().isNotEmpty())
        log("user-applet-links: ${links.data?.size}")

        val profile = UserProfileService(c).getUserProfile()
        assertNotNull(profile.data?.appLoginSubject?.guid)
        log("profile subject ok, ${profile.data?.appLoginPrincipalList?.size} principals")
    }

    @Test
    fun companyLocationAndAppletLoginSubject() = live { c ->
        val all = LocationService(c).getLocationAll().data.orEmpty()
        val loc = all.first().bl_inv_mst_location
        val byGuid = LocationService(c).getLocationByGuid(loc.guid!!).data!!
        assertEquals(loc.guid, byGuid.bl_inv_mst_location.guid)
        val company = CompanyService(c).getCompanyByGuid(loc.guid_bl_fi_mst_comp!!)
        assertNotNull(company.data)
        log("locations: ${all.size}, location -> company ok")

        val links = LoginService(c).getUserAppletLInks().data.orEmpty()
        val stockTake = links.first { it.applet.applet_code == props.getProperty("akn.appletCode.stocktake", "stockTakeAppletV2") && it.tenant.tenant_code == tenantCode }
        val subject = UserProfileService(c).getUserProfile().data!!.appLoginSubject.guid!!
        val alsl = AppletLoginSubjectLinkService(c).getAppletsUsersByCriteria(subject, stockTake.applet.guid, stockTake.tenant.guid, 10)
        log("applet-login-subject query: ${alsl.data.size} of ${alsl.totalRecords}")
    }

    @Test
    fun delivery() = live { c ->
        val trips = TripService(c).getDriverTrips(DriverTripQueryCriteria())
        log("driver-trip-listing: ${trips.data.size} of ${trips.totalRecords}, nested driver lists: ${trips.data.sumOf { it.bl_del_driver_hdrs.size }}")
        val trip = trips.data.firstOrNull() ?: return@live log("no trips, jobs skipped")
        val jobs = DelJobService(c).getJobs(true, "date_created", "DESC", trip.bl_del_trip_hdr.guid!!, 10, 0)
        log("jobs for trip: ${jobs.data.size}")
        val job = jobs.data.firstOrNull() ?: return@live
        val links = JobDocHdrLinkService(c).getJobDocHdrLinkByCriteria(job.bl_del_job_hdr.guid!!, true, "DESC", 10, 0)
        log("job doc-hdr links: ${links.data.size}")
    }

    @Test
    fun genericDocuments() = live { c ->
        val docType = "internal-sales-orders"
        val snap = BaseGenDocTypeService(c).getGenericDocumentByDTOSnapshot(docType, GenericDocumentDto(limit = 5, order = "DESC", orderBy = "date_txn"))
        assertTrue(snap.data.isNotEmpty(), "no $docType on staging")
        val guid = snap.data.first().bl_fi_generic_doc_hdr.guid!!
        assertEquals(guid, BaseGenDocTypeService(c).getGenericDocumentByGuid(docType, guid).data?.bl_fi_generic_doc_hdr?.guid)
        log("snapshot: ${snap.data.size} of ${snap.totalRecords}, by guid ok")

        val imports = GenericDocumentHdrImportService(c).getGenericDocumentHdrLineLinkOpenQueueByCriteria(docType, null, null, 0, 5, null)
        log("hdr-imports: ${imports.data.size} of ${imports.totalRecords}")

        val scan = GenDocScanLineService(c)
        log("scan lines (all): ${scan.getAllGenDocScanLine().data?.size}")
        val lines = scan.getByCriteriaGenDocScanLine().data
        log("scan lines (query): ${lines.size}")
        val line = lines.firstOrNull()?.bl_fi_generic_doc_scan_line ?: return@live
        assertEquals(line.guid, scan.getByGuidGenDocScanLine(line.guid!!).data?.bl_fi_generic_doc_scan_line?.guid)
        log("qty discrepancy: ${scan.getQtyDiscrepancyByHdrGuid(line.generic_doc_hdr_guid!!).data?.size}")
    }

    @Test
    fun inventoryAndSerials() = live { c ->
        val inv = InvItemService(c)
        val items = inv.getInventoryItemByCriteria(limit = 5).data.orEmpty()
        val hdr = items.first().bl_inv_mst_item_hdr
        assertEquals(hdr.guid, inv.getInventoryItemByGuid(hdr.guid!!).data?.bl_inv_mst_item_hdr?.guid)
        log("items: ${items.size}, by guid ok")
        log("item hdrs: ${InvItemHdrService(c).getInventoryItemHdrsByCriteria(hdr.guid_fi_mst_item ?: hdr.guid!!).data.size}")

        val loc = LocationService(c).getLocationAll().data.orEmpty().first().bl_inv_mst_location.guid!!
        log("stock-take item by code: ${inv.getInventoryItemFromItemCode(hdr.code.orEmpty(), loc).data?.size}")
        log("serial/batch lookup (unknown code): ${inv.getInventoryItemFromSerialOrBatch("SDK-LIVE-NONE", loc).data?.size}")
        // #6 fix 5: name is null, so only the old SDK would have left it out and got 400
        val byCode = inv.getInventoryItemByNameAndCode(name = null, code = hdr.code)
        assertEquals("OK_RESPONSE", byCode.code)
        log("search-name-code: ${byCode.data?.size}")

        val serials = SerialNumberService(c).getSerialNumbersByCriteria(limit = 5)
        assertIs<ApiResult.Success<*>>(serials, "serial query: $serials")
        log("serials: ${(serials as ApiResult.Success).data.data.size}")

        // Backend needs fi_item_guid and serialNumbers (it throws NPE without them)
        val dto = SerialNumberValidationDto(txn_type = "SALES", serialNumberList = setOf("SDK-LIVE-NONE"), serialNumbers = setOf(SerialNumberStatus(sn_id = "SDK-LIVE-NONE")),
            fi_item_guid = hdr.guid_fi_mst_item, inv_item_guid = hdr.guid, location_guid = loc)
        log("serial validation: ${SerialNumberService(c).validateSerialNumberList(dto).code}")
        val multi = SerialNumberService(c).validateSerialNumbers(dto)
        assertFalse(multi is ApiResult.Exception, "multi-validation: $multi")
        log("serial multi-validation: " + when (multi) {
            is ApiResult.Success -> "ok ${multi.data.code}"
            is ApiResult.Error -> "HTTP ${multi.code} ${multi.message?.take(300)}"
            is ApiResult.Exception -> "exception"
        })
    }

    @Test
    fun stockTakeReads() = live { c ->
        val devices = StockTakeDeviceService(c)
        val all = devices.getAllStockTakeDevice().data.orEmpty()
        val device = all.first().bl_inv_stock_take_device
        assertEquals(device.guid, devices.getStockTakeDeviceByGuid(device.guid!!).data?.bl_inv_stock_take_device?.guid)
        log("devices: ${all.size}, by code: ${devices.getStockTakeDeviceByCriteria(code = device.code.orEmpty()).data.size}")

        val sessions = StockTakeSessionService(c)
        // #6 fix 4: headers decode as headers, not empty device models
        val hdrs = sessions.getStockTakeSessionHdrByQuery(limit = 5).data
        assertTrue(hdrs.isNotEmpty() && hdrs.all { it.bl_inv_stock_take_session_hdr.guid != null })
        val hdr = hdrs.first().bl_inv_stock_take_session_hdr
        assertEquals(hdr.guid, sessions.getStockTakeSessionHdrByGuid(hdr.guid!!).data?.bl_inv_stock_take_session_hdr?.guid)
        log("session hdrs: ${hdrs.size}, all: ${sessions.getAllStockTakeSessionHdr().data?.size}, by device: ${sessions.getStockTakeSessionByDeviceGuid(device.guid!!).data?.size}")

        val sd = devices.getStockTakeSessionDeviceByCriteria(sessionGuid = hdr.guid!!).data
        log("session devices for hdr: ${sd.size}")
        sd.firstOrNull()?.bl_inv_stock_take_session_device?.guid?.let { // #6 fix 3
            assertEquals(it, devices.getStockTakeSessionDeviceByGuid(it).data?.bl_inv_stock_take_session_device?.guid)
            log("session device by guid ok")
        }

        val records = StockTakeRecordService(c)
        val recs = records.getStokeTakeSessionRecordByCriteria(limit = 5).data
        log("records: ${recs.size}, with inv items: ${records.getStockTakeRecordWithInvItemByCriteria(limit = 5).data.size}")
        recs.firstOrNull()?.bl_inv_stock_take_session_record?.guid?.let {
            assertEquals(it, records.getStokeTakeSessionRecordByGuid(it).data?.bl_inv_stock_take_session_record?.guid)
        }

        val offline = StockTakeOfflineService(c)
        assertNotNull(offline.getAllOfflineStockTakeInfoForDevice(device.code!!).data)
        log("offline device info ok, offline items: ${offline.getInventoryItemsForOffline().data?.size}")
    }

    // ---------- B. write probe that cannot persist ----------

    @Test
    fun multipartUploadIsParsed() = live { c ->
        val data = JobAttachmentModel(bl_del_job_attachment(del_job_hdr_guid = "00000000-0000-0000-0000-000000000000", file_name = "sdk-live.txt"))
        try {
            val res = JobAttachmentService(c).createJobAttachment(listOf(FileUpload("sdk-live.txt", "SDK-LIVE".encodeToByteArray(), "text/plain")), data)
            log("multipart upload unexpectedly succeeded: ${res.code} ${res.data?.map { it.bl_del_job_attachment.guid }}")
        } catch (e: ResponseException) {
            val body = e.response.bodyAsText()
            log("multipart upload rejected after parsing: ${e.response.status} $body")
            // A body the server could not parse fails with these, before any job lookup
            listOf("Multipart", "Required request part", "MALFORMED").forEach { assertFalse(body.contains(it, ignoreCase = true), body) }
        }
    }

    // ---------- C. round trips (create -> read -> update -> delete) ----------

    @Test
    fun stockTakeSessionDeviceAndRecordRoundTrip() = live { c ->
        val devices = StockTakeDeviceService(c)
        val records = StockTakeRecordService(c)
        val device = devices.getAllStockTakeDevice().data.orEmpty().first().bl_inv_stock_take_device
        val hdr = StockTakeSessionService(c).getStockTakeSessionHdrByQuery(limit = 5).data.first().bl_inv_stock_take_session_hdr
        val item = InvItemService(c).getInventoryItemByCriteria(limit = 1).data.orEmpty().first().bl_inv_mst_item_hdr

        val sd = devices.createStockTakeSessionDevice(StockTakeSessionDeviceModel(bl_inv_stock_take_session_device(session_guid = hdr.guid, device_guid = device.guid, status = "ACTIVE")))
            .data?.bl_inv_stock_take_session_device ?: fail("session device create returned no data")
        val sdGuid = sd.guid!!
        val created = mutableListOf<String>()
        try {
            assertEquals(sdGuid, devices.getStockTakeSessionDeviceByGuid(sdGuid).data?.bl_inv_stock_take_session_device?.guid)
            devices.updateStockTakeSessionDevice(StockTakeSessionDeviceModel(sd.copy(status = "ACTIVE")))
            log("session device created/read/updated: $sdGuid")

            fun record(qty: Double) = StockTakeSessionRecordModel(bl_inv_stock_take_session_record(
                guid_session_hdr = hdr.guid, guid_session_device = sdGuid, guid_comp = hdr.guid_comp, guid_location = hdr.guid_location,
                item_guid = item.guid, item_code = item.code, item_name = item.name, qty = qty, status = "ACTIVE"))

            val single = records.createStokeTakeSessionRecord(record(1.0)).data.orEmpty().map { it.bl_inv_stock_take_session_record }
            created += single.mapNotNull { it.guid }
            assertTrue(created.isNotEmpty(), "record create returned no guid")
            val got = records.getStokeTakeSessionRecordByGuid(created.first()).data!!.bl_inv_stock_take_session_record
            records.updateStokeTakeSessionRecord(StockTakeSessionRecordModel(got.copy(qty = 2.0)))
            log("record created/read/updated: ${created.first()}")

            // #6 fix 2: /multi takes a JSON array
            val multi = records.createMultiStokeTakeSessionRecord(listOf(record(3.0))).data.orEmpty().map { it.bl_inv_stock_take_session_record }
            created += multi.mapNotNull { it.guid }
            log("multi create: ${multi.size}")
            records.updateMultiStokeTakeSessionRecord(multi.map { StockTakeSessionRecordModel(it.copy(qty = 4.0)) })
            log("multi update ok")
        } finally {
            created.forEach { runCatching { records.deleteStokeTakeSessionRecord(it) }.onFailure { e -> log("CLEANUP FAILED record $it: $e") } }
            runCatching { devices.deleteStockTakeSessionDevice(sdGuid) }.onFailure { e -> log("CLEANUP FAILED session device $sdGuid: $e") }
            log("cleanup: ${created.size} records, 1 session device")
        }
    }

    @Test
    fun genDocScanLineRoundTrip() = live { c ->
        val scan = GenDocScanLineService(c)
        // Copy a real line so the document/applet fields are consistent; item_guid is the FI item (bl_fi_mst_item_hdr), and staging lines can point at deleted ones
        val template = scan.getByCriteriaGenDocScanLine().data.first().bl_fi_generic_doc_scan_line
        val item = InvItemService(c).getInventoryItemByCriteria(limit = 1).data.orEmpty().first().bl_inv_mst_item_hdr
        fun line(qty: Double) = GenericDocumentScanLineModel(template.copy(
            guid = null, qty = qty, item_guid = item.guid_fi_mst_item, item_code = item.code, item_name = "SDK-LIVE ${item.name}"))

        val created = mutableListOf<String>()
        try {
            val one = scan.createGenDocScanLine(line(1.0)).data?.bl_fi_generic_doc_scan_line ?: fail("scan line create returned no data")
            created += one.guid!!
            assertEquals(one.guid, scan.getByGuidGenDocScanLine(one.guid!!).data?.bl_fi_generic_doc_scan_line?.guid)
            scan.updateGenDocScanLine(GenericDocumentScanLineModel(one.copy(qty = 2.0)))
            log("scan line created/read/updated: ${one.guid}")

            val multi = scan.createMultiGenDocScanLine(listOf(line(3.0))).data.orEmpty()
            created += multi.mapNotNull { it.bl_fi_generic_doc_scan_line.guid }
            log("multi scan line create: ${multi.size}")
        } finally {
            created.forEach { runCatching { scan.deleteGenDocScanLine(it) }.onFailure { e -> log("CLEANUP FAILED scan line $it: $e") } }
            log("cleanup: ${created.size} scan lines")
        }
    }
}
