package com.bloomi.demo

import com.bloomi.demo.mappers.ActivityMapper
import com.bloomi.demo.models.entities.Activity
import com.bloomi.demo.models.entities.ActivityType
import com.bloomi.demo.models.requests.CreateActivityRequest
import com.bloomi.demo.models.responses.ActivityResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ActivityMapperTest {

    private val mapper = ActivityMapper()

    @Test
    fun `should map CreateActivityRequest to Activity entity`() {
        val request = CreateActivityRequest(
            title = "Limpieza",
            type = ActivityType.MAINTENANCE,
            description = "Limpieza de quebradas",
            location = "Parque Metropolitano",
            date = LocalDate.of(2025, 8, 1),
            foundationId = "f1"
        )

        val result = mapper.toEntity(request)

        assertEquals("Limpieza", result.title)
        assertEquals(ActivityType.MAINTENANCE.name, result.type)
        assertEquals("Limpieza de quebradas", result.description)
        assertEquals("Parque Metropolitano", result.location)
        assertEquals(LocalDate.of(2025, 8, 1), result.date)
        assertEquals("f1", result.foundationId)
    }

    @Test
    fun `should map Activity entity to ActivityResponse`() {
        val activity = Activity(
            id = "a1",
            title = "Reforestación",
            type = ActivityType.REFORESTATION.name,
            description = "Plantar árboles nativos",
            location = "Reserva El Ángel",
            date = LocalDate.of(2025, 9, 15),
            foundationId = "fund2"
        )

        val response = mapper.toResponse(activity)

        assertEquals("a1", response.id)
        assertEquals("Reforestación", response.title)
        assertEquals(ActivityType.REFORESTATION, response.type)
        assertEquals("Plantar árboles nativos", response.description)
        assertEquals("Reserva El Ángel", response.location)
        assertEquals(LocalDate.of(2025, 9, 15), response.date)
        assertEquals("fund2", response.foundationId)
    }
}