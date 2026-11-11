package com.akaun.kmp.sdk.models

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
import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobDocHdrLinkModel
import com.akaun.kmp.sdk.models.aggregates.erp.deljob.JobModel
import com.akaun.kmp.sdk.models.aggregates.erp.delshipment.DelJobShipmentLinkModel
import com.akaun.kmp.sdk.models.aggregates.erp.delshipment.DelShipmentProcessingOpenQueueModel
import com.akaun.kmp.sdk.models.aggregates.erp.pickpackqueue.FinanciaPickPackQueueModel
import com.akaun.kmp.sdk.models.aggregates.erp.trip.TripsDriverVehicleModel
import com.akaun.kmp.sdk.models.dbschema.bl_del_job_hdr
import com.akaun.kmp.sdk.models.dbschema.bl_del_trip_ext
import com.akaun.kmp.sdk.models.dbschema.bl_del_trip_hdr
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.ApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.BasicApiResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.apiservices.shared.PagingResponseModel
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.JobHdrListingQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.criteria.TripsPostQueryCriteria
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.JobsDeliveryStatusDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.JobsDto
import com.akaun.kmp.sdk.services.comakaunapi.core2.dto.TripListingWithDriverOutputDTO
import kotlinx.serialization.json.JsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Instant

class DeliveryModelsTest {

    @Test
    fun jobsDecodeWithExtEventsAndAttachments() {
        val job = AknJson.decodeFromString<ApiResponseModel<JobModel>>(JOBS_JSON).data!!.first()

        assertEquals("a3719353-b4b1-42cf-a04c-8f6009ca45bc", job.bl_del_job_hdr.guid)
        assertEquals("DELIVERY_REARRANGED", job.bl_del_job_hdr.delivery_status)
        assertEquals("JOB_STATUS", job.bl_del_job_ext.first().param_code)
        assertEquals(1, job.bl_del_job_attachments.size)
        // Map<String, Any>? column -> JsonObject?
        assertIs<JsonObject>(job.bl_del_job_event.first().message_json)
    }

    @Test
    fun jobColumnsDecodeToBackendTypes() {
        val hdr = AknJson.decodeFromString<ApiResponseModel<JobModel>>(JOBS_JSON).data!!.first().bl_del_job_hdr

        // ZonedDateTime -> Instant, Integer -> Int, Map -> JsonObject (issue #12)
        assertEquals(Instant.parse("2025-10-13T03:22:11.84Z"), hdr.start_time)
        assertEquals(Instant.parse("2025-10-13T03:22:28.355906Z"), hdr.date_created)
        assertEquals(120, hdr.duration_minute)
        assertEquals(1, hdr.ext_delivery_json!!.size)
        // BigDecimal -> Double
        val link = AknJson.decodeFromString<ApiResponseModel<DelJobShipmentLinkModel>>(JOB_SHIPMENT_LINKS_JSON).data!!.first()
        assertEquals(5.0, link.bl_del_job_shipment_link.quantity)

        // Sending a decoded row back keeps every value: dates go out as ISO-8601 UTC, which the backend parses
        val json = AknJson.encodeToString(bl_del_job_hdr.serializer(), hdr)
        assertTrue("\"date_created\":\"2025-10-13T03:22:28.355906Z\"" in json, json)
        assertEquals(hdr, AknJson.decodeFromString(bl_del_job_hdr.serializer(), json))
    }

    @Test
    fun datesWithAnOffsetDecodeToTheSameInstant() {
        val row = AknJson.decodeFromString(bl_del_trip_hdr.serializer(), """{"date_created":"2025-10-13T11:22:28.355906+08:00"}""")
        assertEquals(Instant.parse("2025-10-13T03:22:28.355906Z"), row.date_created)
    }

    @Test
    fun tripExtAclColumnsDecodeAsObjects() {
        // Backend type is Map<String, Object>; the old String? field failed on any non-null value
        val ext = AknJson.decodeFromString(bl_del_trip_ext.serializer(), """{"acl_config":{"read":["ADMIN"]},"acl_policy":{}}""")
        assertEquals(setOf("read"), ext.acl_config!!.keys)
        assertEquals(JsonObject(emptyMap()), ext.acl_policy)
    }

    @Test
    fun tripWithDriverVehicleAndRegionDecodes() {
        val page = AknJson.decodeFromString<PagingResponseModel<TripsDriverVehicleModel>>(TRIPS_GET_TRIP_JSON)

        val trip = page.data.first()
        assertEquals("047ce0a1-2d2c-4db1-80fc-79ec01ef52ad", trip.bl_del_trip_hdr.guid)
        assertEquals("11", trip.bl_del_trip_hdr.running_number_01) // backend sends the number 11
        assertEquals("MLK-02", trip.bl_del_region_hdr.region_code)
        assertEquals("scania", trip.bl_del_vehicle_hdr.brand)
        assertEquals(2, trip.bl_del_driver_hdr.size)
        assertEquals(0, page.data[1].bl_del_driver_hdr.size) // trips without a driver send []
        assertEquals(180, page.totalRecords)
    }

    @Test
    fun driverTripListingDecodes() {
        val page = AknJson.decodeFromString<PagingResponseModel<TripListingWithDriverOutputDTO>>(DRIVER_TRIP_LISTING_JSON)

        val first = page.data.first()
        assertEquals("047ce0a1-2d2c-4db1-80fc-79ec01ef52ad", first.bl_del_trip_hdr.guid)
        // One driver list per trip-driver link (backend type List<List<bl_del_driver_hdr>>)
        assertEquals(1, first.bl_del_trip_driver_links.size)
        assertEquals(1, first.bl_del_driver_hdrs.single().size)
        assertEquals(emptyList(), page.data[1].bl_del_driver_hdrs)
    }

    @Test
    fun linkAndQueueTablesDecode() {
        assertEquals(
            "2a174b16-d332-4003-85b5-56fa0c542760",
            AknJson.decodeFromString<ApiResponseModel<JobAttachmentModel>>(JOB_ATTACHMENTS_JSON).data!!.first().bl_del_job_attachment.guid,
        )
        assertEquals(
            "f371c5aa-2358-4ba1-9de8-4a8120b8fb10",
            AknJson.decodeFromString<ApiResponseModel<DelJobShipmentLinkModel>>(JOB_SHIPMENT_LINKS_JSON).data!!.first().bl_del_job_shipment_link.guid,
        )
        assertEquals(
            "c074cc7c-6dc8-e7b9-3ad0-cb2881dbc86e",
            AknJson.decodeFromString<ApiResponseModel<JobDocHdrLinkModel>>(JOB_DOCHDR_LINKS_JSON).data!!.first().bl_del_job_dochdr_link.guid,
        )
        assertEquals(
            "30f44359-a6f8-4c73-a9a7-b2a00ccbde6c",
            AknJson.decodeFromString<ApiResponseModel<DelShipmentProcessingOpenQueueModel>>(SHIPMENT_PROCESSING_OPEN_QUEUE_JSON)
                .data!!.first().bl_del_shipment_processing_open_queue.guid,
        )
        assertEquals(
            "b8639728-be76-47ed-aaac-99245583e2be",
            AknJson.decodeFromString<ApiResponseModel<FinanciaPickPackQueueModel>>(PICK_PACK_QUEUE_JSON).data!!.first().bl_fi_pick_pack_queue.guid,
        )
    }

    @Test
    fun jobsDeliveryStatusDecodes() {
        val status = AknJson.decodeFromString<BasicApiResponseModel<JobsDeliveryStatusDto>>(JOBS_DELIVERY_STATUS_JSON).data!!

        assertEquals("9c64ec5d-b135-9f96-ebd6-41d219bd00e0", status.bl_del_job_hdr.guid)
        assertEquals("9c64ec5d-b135-9f96-ebd6-41d219bd00e0", status.bl_del_job_dochdr_links.single().hdr_guid)
        assertEquals(listOf("3.0", "1.0"), status.bl_del_job_docline_links.map { it.qty_to_deliver }) // numbers into String?
        assertEquals(emptyList(), status.bl_del_job_shipment_links)
        assertEquals("e3674c9b-f919-4133-9f70-58592384c3e9", status.bl_del_job_event.guid)
    }

    @Test
    fun requestBodiesEncodeSetsAsArraysAndOmitNulls() {
        assertEquals(
            """{"jobs_guids":["j1","j2"],"tripHdrGuid":"t1"}""",
            AknJson.encodeToString(JobsDto.serializer(), JobsDto(jobs_guids = setOf("j1", "j2"), tripHdrGuid = "t1")),
        )
        assertEquals(
            """{"job_hdr_guids":["j1"],"delivery_status":["PENDING"]}""",
            AknJson.encodeToString(
                JobHdrListingQueryCriteria.serializer(),
                JobHdrListingQueryCriteria(job_hdr_guids = setOf("j1"), delivery_status = setOf("PENDING")),
            ),
        )
        val trips = AknJson.encodeToString(TripsPostQueryCriteria.serializer(), TripsPostQueryCriteria(trip_guids = setOf("t1")))
        assertTrue("\"trip_guids\":[\"t1\"]" in trips, trips)
    }
}
