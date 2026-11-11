package com.akaun.kmp.sdk.services

import com.akaun.kmp.sdk.client.AknJson
import com.akaun.kmp.sdk.fixtures.DRIVER_TRIP_LISTING_JSON
import com.akaun.kmp.sdk.fixtures.JOBS_DELIVERY_STATUS_JSON
import com.akaun.kmp.sdk.fixtures.JOBS_JSON
import com.akaun.kmp.sdk.fixtures.JOB_ATTACHMENTS_JSON
import com.akaun.kmp.sdk.fixtures.JOB_DOCHDR_LINKS_JSON
import com.akaun.kmp.sdk.fixtures.JOB_SHIPMENT_LINKS_JSON
import com.akaun.kmp.sdk.fixtures.PICK_PACK_QUEUE_JSON
import com.akaun.kmp.sdk.fixtures.SHIPMENT_PROCESSING_OPEN_QUEUE_JSON
import com.akaun.kmp.sdk.fixtures.TRIPS_GET_TRIP_JSON
import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobAttachmentModel
import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.DelJobService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.DelJobShipmentLinkService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.JobAttachmentService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.JobDocHdrLinkService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.deljobservices.JobsDocService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.delshipmentservices.DelShipmentHdrService
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.FileUpload
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.tripservices.TripService
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.DriverTripQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.JobHdrListingQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.TripsPostQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.JobsDto
import com.akaun.kmp.sdk.support.MockAknClient
import com.akaun.kmp.sdk.support.MockAknClient.Companion.BASE_URL
import com.akaun.kmp.sdk.support.bodyText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.jsonObject
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeliveryServicesTest {

    /** Re-wraps a list fixture (ApiResponseModel) as a PagingResponseModel response. */
    private fun paged(fixture: String): String {
        val data = AknJson.parseToJsonElement(fixture).jsonObject["data"]
        return """{"totalRecords":0,"offset":0,"limit":20,"code":"OK_RESPONSE","message":"","data":$data}"""
    }

    @Test
    fun getJobsSendsAllQueryParams() = runTest {
        val mock = MockAknClient(responseJson = paged(JOBS_JSON))

        val page = DelJobService(mock.client).getJobs(true, "date_created", "DESC", "trip1", 10, 0)

        assertEquals(
            "${BASE_URL}core2/tnt/dm/del/jobs/backoffice-ep/query?calcTotalRecords=true&orderBy=date_created&order=DESC&guid_trip_hdr=trip1&limit=10&offset=0",
            mock.requests.single().url.toString(),
        )
        assertEquals("a3719353-b4b1-42cf-a04c-8f6009ca45bc", page.data.first().bl_del_job_hdr.guid)
    }

    @Test
    fun jobListingCompleteAndUpdate() = runTest {
        val listing = MockAknClient(responseJson = JOBS_JSON)
        DelJobService(listing.client).getJobHdrListingForLoginDriverByPost(JobHdrListingQueryCriteria(job_hdr_guids = setOf("j1")))
        assertEquals("${BASE_URL}core2/tnt/dm/del/jobs/login-driver-ep/job-hdr-listings/query", listing.requests.single().url.toString())
        assertEquals("""{"job_hdr_guids":["j1"]}""", listing.requests.single().bodyText())

        val complete = MockAknClient(responseJson = JOBS_DELIVERY_STATUS_JSON)
        val status = DelJobService(complete.client).completeJobsLoginDriverEp("j1")
        assertEquals(HttpMethod.Post, complete.requests.single().method)
        assertEquals("${BASE_URL}core2/tnt/dm/del/jobs/login-driver-ep/complete-jobs/j1", complete.requests.single().url.toString())
        assertEquals("9c64ec5d-b135-9f96-ebd6-41d219bd00e0", status.data!!.bl_del_job_hdr.guid)

        val job = AknJson.decodeFromString<ApiResponseModel<JobModel>>(JOBS_JSON).data!!.first()
        val update = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":${AknJson.encodeToString(JobModel.serializer(), job)}}""")
        val updated = DelJobService(update.client).updateJob(job)
        assertEquals(HttpMethod.Put, update.requests.single().method)
        assertEquals("${BASE_URL}core2/tnt/dm/del/jobs", update.requests.single().url.toString())
        assertEquals(job, updated.data)
    }

    @Test
    fun shipmentLinksSendJobGuidsAsRepeatedKeys() = runTest {
        val mock = MockAknClient(responseJson = paged(JOB_SHIPMENT_LINKS_JSON))

        val page = DelJobShipmentLinkService(mock.client).getJobShipmentLinkByCriteriaLoginDriver(limit = "20", jobHdrGuids = linkedSetOf("j1", "j2"))

        // Retrofit sent job_hdr_guids=j1&job_hdr_guids=j2; Ktor's parameter() would send "[j1, j2]"
        assertEquals(
            "${BASE_URL}core2/tnt/dm/del/job-shipment-links/login-driver-ep/query?calcTotalRecords=true&limit=20&job_hdr_guids=j1&job_hdr_guids=j2",
            mock.requests.single().url.toString(),
        )
        assertEquals("f371c5aa-2358-4ba1-9de8-4a8120b8fb10", page.data.first().bl_del_job_shipment_link.guid)
    }

    @Test
    fun attachmentUploadSendsFilesAndJsonDataParts() = runTest {
        val model = AknJson.decodeFromString<ApiResponseModel<JobAttachmentModel>>(JOB_ATTACHMENTS_JSON).data!!.first()
        val mock = MockAknClient(responseJson = JOB_ATTACHMENTS_JSON)

        val created = JobAttachmentService(mock.client).createJobAttachment(
            files = listOf(
                FileUpload("proof.jpg", byteArrayOf(1, 2, 3), "image/jpeg"),
                FileUpload("sign.png", byteArrayOf(4, 5), "image/png"),
            ),
            data = model,
        )

        val request = mock.requests.single()
        assertEquals("${BASE_URL}core2/tnt/dm/del/job-attachments/login-driver-ep", request.url.toString())
        assertEquals("multipart/form-data", request.body.contentType!!.withoutParameters().toString())
        val body = request.bodyText()
        assertEquals(2, Regex("""name="?files"?; filename="?(proof\.jpg|sign\.png)"?""").findAll(body).count(), body)
        assertTrue("Content-Type: image/jpeg" in body, body)
        assertTrue(Regex("""name="?data"?""").containsMatchIn(body), body)
        assertTrue("Content-Type: application/json" in body, body)
        assertTrue("\"guid\":\"2a174b16-d332-4003-85b5-56fa0c542760\"" in body, body)
        assertEquals(model.bl_del_job_attachment.guid, created.data!!.first().bl_del_job_attachment.guid)
    }

    @Test
    fun attachmentDownloadReturnsBytes() = runTest {
        val mock = MockAknClient(responseJson = "FILEBYTES")

        val bytes = JobAttachmentService(mock.client).getJobAttachmentFileByGuidLoginDriver("a1")

        assertEquals("${BASE_URL}core2/tnt/dm/del/job-attachments/login-driver-ep/file/a1", mock.requests.single().url.toString())
        assertContentEquals("FILEBYTES".encodeToByteArray(), bytes)
    }

    @Test
    fun cancelJobAndCancelFromShipment() = runTest {
        val dto = JobsDto(jobs_guids = setOf("j1"), tripHdrGuid = "t1")

        val cancel = MockAknClient(responseJson = PICK_PACK_QUEUE_JSON)
        val queue = JobsDocService(cancel.client).cancelJobLoginDriver(dto)
        assertEquals("${BASE_URL}core2/tnt/dm/del/job-docs/cancel-job/login-driver-ep", cancel.requests.single().url.toString())
        assertEquals("""{"jobs_guids":["j1"],"tripHdrGuid":"t1"}""", cancel.requests.single().bodyText())
        assertEquals("b8639728-be76-47ed-aaac-99245583e2be", queue.data!!.first().bl_fi_pick_pack_queue.guid)

        val fromShipment = MockAknClient(responseJson = """{"code":"OK_RESPONSE","message":"","data":[{"guid":"30f44359-a6f8-4c73-a9a7-b2a00ccbde6c"}]}""")
        val open = DelShipmentHdrService(fromShipment.client).cancelJobsFromShipmentLoginDriverEp(dto)
        assertEquals("${BASE_URL}core2/tnt/dm/delivery-shipment-hdr/login-driver-ep/cancel-jobs-from-shipment", fromShipment.requests.single().url.toString())
        assertEquals("30f44359-a6f8-4c73-a9a7-b2a00ccbde6c", open.data!!.single().guid)
    }

    @Test
    fun jobDocHdrLinks() = runTest {
        val mock = MockAknClient(responseJson = paged(JOB_DOCHDR_LINKS_JSON))

        val page = JobDocHdrLinkService(mock.client).getJobDocHdrLinkByCriteria("j1", true, "DESC", 20, 0)

        assertEquals(
            "${BASE_URL}core2/tnt/dm/del/job-dochdr-links/query?hdr_guid=j1&calcTotalRecords=true&order=DESC&limit=20&offset=0",
            mock.requests.single().url.toString(),
        )
        assertEquals("c074cc7c-6dc8-e7b9-3ad0-cb2881dbc86e", page.data.first().bl_del_job_dochdr_link.guid)
    }

    @Test
    fun trips() = runTest {
        val listing = MockAknClient(responseJson = DRIVER_TRIP_LISTING_JSON)
        val trips = TripService(listing.client).getDriverTrips(DriverTripQueryCriteria(guid_driver_hdr = "d1"))
        assertEquals("${BASE_URL}core2/tnt/dm/del/trips/backoffice-ep/driver-trip-listing/query", listing.requests.single().url.toString())
        assertEquals("""{"guid_driver_hdr":"d1"}""", listing.requests.single().bodyText())
        assertEquals(1, trips.data.first().bl_del_driver_hdrs.single().size)

        val driver = MockAknClient(responseJson = TRIPS_GET_TRIP_JSON)
        val mine = TripService(driver.client).getDriverTripsLoginDriverEp(TripsPostQueryCriteria(trip_guids = setOf("t1")))
        assertEquals("${BASE_URL}core2/tnt/dm/del/trips/login-driver-ep/getTrip/query", driver.requests.single().url.toString())
        assertEquals("MLK-02", mine.data.first().bl_del_region_hdr.region_code)
    }
}
