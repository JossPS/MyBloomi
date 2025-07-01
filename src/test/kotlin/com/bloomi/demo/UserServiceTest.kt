package com.bloomi.demo

import com.bloomi.demo.exceptions.exceptions.ResourceNotFoundException
import com.bloomi.demo.mappers.ActivityMapper
import com.bloomi.demo.mappers.UserMapper
import com.bloomi.demo.models.entities.Activity
import com.bloomi.demo.models.entities.User
import com.bloomi.demo.models.requests.CreateUserRequest
import com.bloomi.demo.repositories.ActivityRepository
import com.bloomi.demo.repositories.UserRepository
import com.bloomi.demo.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import java.time.LocalDate

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var activityRepository: ActivityRepository
    private lateinit var userMapper: UserMapper
    private lateinit var activityMapper: ActivityMapper
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        activityRepository = mock(ActivityRepository::class.java)
        userMapper = UserMapper()
        activityMapper = ActivityMapper()
        userService = UserService(userRepository, userMapper, activityRepository, activityMapper)
    }
///Test para crear al usuario
    @Test
    fun should_create_a_user_successfully (){
        val request = CreateUserRequest(
            name = "Jorge",
            email = "jorge23@gmail.com"
        )

        val user = userMapper.toEntity(request)
        `when`(userRepository.save(any<User>())).thenReturn(user)

        val result = userService.createUser(request)

        assertEquals("Jorge", result.name)
        assertEquals("jorge23@gmail.com", result.email)
    }

    /// Test para llamar al usuario si es que ya esta creado
    @Test
    fun should_get_user_by_ID_i_exists() {
        val userId = "u1"
        val user = User(id = userId, name = "Jorge", email = "jorge23@mail.com", joinedActivities = listOf())

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))

        val result = userService.getUserById(userId)

        assertEquals("Jorge", result.name)
        assertEquals(userId, result.id)
    }

    // Excepcion para cuando no esta creado el usuario
    @Test
    fun should_throw_exception_if_user_not_found_by_ID() {
        `when`(userRepository.findById("404")).thenReturn(Optional.empty())

        assertFailsWith<ResourceNotFoundException> {
            userService.getUserById("404")
        }
    }

    ///prueba para inscribir a un usuario a una actividad
    @Test
    fun should_allow_user_to_join_new_activity(){
        val userId = "u1"
        val activityId = "a1"

        val existingUser = User(
            id = userId,
            name = "Jorge",
            email = "jorge23@gmail.com",
            joinedActivities = listOf()
        )
       val updatedUser = existingUser.copy(joinedActivities = listOf(activityId))
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(existingUser))
        `when`(userRepository.save(any<User>())).thenReturn(updatedUser)

        val result = userService.joinActivity(userId, activityId)

        assertEquals(listOf(activityId), result.joinedActivities)

    }
    //// prueba para evitar la duplicacion de inscripccion de los usuarios a actividades
    @Test
    fun should_not_duplicate_activity_if_already_joined(){
        val userId = "u1"
        val activityId = "a1"

        val existingUser = User(
            id = userId,
            name = "Jorge",
            email = "jorge23@gmail.com",
            joinedActivities = listOf(activityId)
        )

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(existingUser))
        val result = userService.joinActivity(userId, activityId)

        assertEquals(1, result.joinedActivities.size)
        assertEquals(activityId, result.joinedActivities.first())

    }

    //Retornara las excepciones cuando un usuario no se encuentre inscrito en una actividad
    @Test
    fun should_throw_exception_if_user_not_found_when_join_activity (){
        val userId = "nope"
        val activityId = "a1"

        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        assertFailsWith<ResourceNotFoundException>{
            userService.joinActivity(userId, activityId)
        }
    }

    //Retornara las actividades a las que esta inscrito el usuario
    @Test
    fun should_return_activities_joined_by_user(){
        val userId = "u1"
        val joinedIds = listOf("a1", "a2")

        val user = User(
            id = userId,
            name = "Jorge",
            email = "jorge23@gmail.com",
            joinedActivities =  joinedIds
        )

        val activity1 = Activity(
            id = "act1",
            title = "Reforestación",
            description = "Plantación",
            type = "REFORESTATION",
            location = "Quito",
            date = LocalDate.now(),
            foundationId = "f1"
        )
        val activity2 = Activity(
            id = "act2",
            title = "Mantenimiento",
            description = "Río",
            type = "MAINTENANCE",
            location = "Cumbaya",
            date = LocalDate.now(),
            foundationId = "f2"
        )

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(activityRepository.findAllById(joinedIds)).thenReturn(listOf(activity1, activity2))

        val result = userService.getUserActivities(userId)

        assertEquals(2, result.size)
        assertEquals("Reforestación", result[0].title)
        assertEquals("Mantenimiento", result[1].title)

    }

    @Test
    fun should_delete_user_if_exists() {
        val userId = "existing-id"

        `when`(userRepository.existsById(userId)).thenReturn(true)

        userService.deleteUser(userId)

        verify(userRepository).deleteById(userId)
    }


    @Test
    fun should_throw_exception_when_deleting_nin_existent_user(){
        val userId = "not-found"

        `when`(userRepository.existsById(userId)).thenReturn(false)

        assertFailsWith<ResourceNotFoundException> {
            userService.deleteUser(userId)
        }
    }




}