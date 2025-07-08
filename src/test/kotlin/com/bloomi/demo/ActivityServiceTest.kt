package com.bloomi.demo

import com.bloomi.demo.exceptions.exceptions.ResourceNotFoundException
import com.bloomi.demo.mappers.ActivityMapper
import com.bloomi.demo.models.entities.Activity
import com.bloomi.demo.models.entities.ActivityType
import com.bloomi.demo.models.requests.CreateActivityRequest
import com.bloomi.demo.models.requests.UpdateActivityRequest
import com.bloomi.demo.repositories.ActivityRepository
import com.bloomi.demo.repositories.UserRepository
import com.bloomi.demo.services.ActivityService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.LocalDate
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ActivityServiceTest {

    private lateinit var activityRepository: ActivityRepository
    private lateinit var userRepository: UserRepository
    private lateinit var activityMapper: ActivityMapper
    private lateinit var activityService: ActivityService

    @BeforeEach
    fun setup() {
        activityRepository = mock(ActivityRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        activityMapper = ActivityMapper()

        activityService = ActivityService(
            activityRepository,
            activityMapper,
            userRepository
        )
    }
////Prueba para crear una nueva actividad
    @Test
    fun should_create_activity() {
        val request = CreateActivityRequest(
            title = "Plantación",
            type = ActivityType.REFORESTATION,
            description = "Reforestación masiva",
            location = "Quito",
            date = LocalDate.now(),
            foundationId = "f1"
        )

        val activity = activityMapper.toEntity(request)

        `when`(activityRepository.save(any(Activity::class.java))).thenReturn(activity)

        val result = activityService.createActivity(request)

        assertEquals(request.title, result.title)
        assertEquals(request.description, result.description)
    }
////prueba para retornar la actividad por ids
    @Test
    fun should_return_activity_by_id() {
        val id = "a1"
        val activity = Activity(
            id = id,
            title = "Limpieza",
            type = ActivityType.MAINTENANCE.name,
            description = "Limpieza de senderos",
            location = "Parque Metropolitano",
            date = LocalDate.parse("2025-08-01"),
            foundationId = "Fundacion Margarita"
        )

        `when`(activityRepository.findById(id)).thenReturn(Optional.of(activity))

        val result = activityService.getActivityById(id)

        assertEquals(activity.title, result.title)
    }
    ////aviso para cuando no se encuentra a una actividad
    @Test
    fun should_throw_when_activity_not_found_by_id() {
        val id = "invalid-id"

        `when`(activityRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            activityService.getActivityById(id)
        }
    }

    ////prueba para llamaar a todsa las actividades
    @Test
    fun should_return_all_activities() {
        val activities = listOf(
            Activity(
                id = "1",
                title = "Mantenimiento",
                type = ActivityType.MAINTENANCE.name,
                description = "Mantenimiento de bosques afectados",
                location = "Parque Metropolitano",
                date = LocalDate.now(),
                foundationId = "f1"
            ),
            Activity(
                id = "2",
                title = "Reforestación",
                type = ActivityType.REFORESTATION.name,
                description = "Reforestación de áreas verdes",
                location = "Sector La Floresta",
                date = LocalDate.now(),
                foundationId = "f2"
            )
        )

        `when`(activityRepository.findAll()).thenReturn(activities)

        val result = activityService.getAllActivities()

        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("2", result[1].id)
    }

    ///test de la funcion para ctualizar una actividad
    @Test
    fun should_update_activity() {
        val id = "a1"
        val existing = Activity(
            id = id,
            title = "Mantenimiento",
            type = ActivityType.MAINTENANCE.name,
            description = "Mamteniemieto de bosque reforestado hace un mes",
            location = "Guapulo",
            date = LocalDate.now(),
            foundationId = "f1"
        )

        val updateRequest = UpdateActivityRequest(
            title = "Mantenimiento",
            description = "Mantenimiento de bosque reforestado hace una semana"
        )

        val updated = existing.copy(
            title = updateRequest.title ?: existing.title,
            description = updateRequest.description ?: existing.description
        )

        `when`(activityRepository.findById(id)).thenReturn(Optional.of(existing))
        `when`(activityRepository.save(any(Activity::class.java))).thenReturn(updated)

        val result = activityService.updateActivity(id, updateRequest)

        assertEquals("Mantenimiento", result.title)
        assertEquals("Mantenimiento de bosque reforestado hace una semana", result.description)
    }

    ///Prueba para borrar una actividad
    @Test
    fun should_delete_activity() {
        val id = "a1"

        `when`(activityRepository.existsById(id)).thenReturn(true)
        doNothing().`when`(activityRepository).deleteById(id)

        activityService.deleteActivity(id)

        verify(activityRepository).deleteById(id)
    }

    ////Excepcion para cuando no se puede eliminar una actibidad nonexistent
    @Test
    fun should_throw_when_deleting_nonexistent_activity() {
        val id = "not-found"
        `when`(activityRepository.existsById(id)).thenReturn(false)

        assertThrows<ResourceNotFoundException> {
            activityService.deleteActivity(id)
        }
    }



}
