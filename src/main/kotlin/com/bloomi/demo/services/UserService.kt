package com.bloomi.demo.services


import com.bloomi.demo.exceptions.exceptions.ResourceNotFoundException
import com.bloomi.demo.mappers.ActivityMapper
import com.bloomi.demo.mappers.UserMapper
import com.bloomi.demo.models.requests.CreateUserRequest
import com.bloomi.demo.models.responses.ActivityResponse
import com.bloomi.demo.models.responses.UserResponse
import com.bloomi.demo.repositories.ActivityRepository
import com.bloomi.demo.repositories.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService (
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val activityRepository: ActivityRepository,
    private val activityMapper: ActivityMapper
){
    fun createUser(request: CreateUserRequest): UserResponse {
        val user = userRepository.save(userMapper.toEntity(request))
        return userMapper.toResponse(user)
    }

    fun getUserById(id: String): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User with ID $id not found")
        }
        return userMapper.toResponse(user)
    }

    fun getAllUsers(): List<UserResponse>{
        return userRepository.findAll().map { userMapper.toResponse(it) }
    }

    fun deleteUser(id: String){
        if (!userRepository.existsById(id)){
            throw ResourceNotFoundException("User with ID $id cannot be deleted because it does not exist ")
        }
    }

    fun joinActivity(userId: String, activityId: String): UserResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User with ID $userId not found")
        }

        // nos premite verificar si ya esta inscrito
        if (user.joinedActivities.contains(activityId)) {
            return userMapper.toResponse(user)
        }

        val updatedUser = user.copy(joinedActivities = user.joinedActivities + activityId)
        return userMapper.toResponse(userRepository.save(updatedUser))
    }

    fun getUserActivities(userId: String): List<ActivityResponse> {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User with ID $userId not found")
        }

        val activities = activityRepository.findAllById(user.joinedActivities)
        return activities.map { activityMapper.toResponse(it) }
    }
}