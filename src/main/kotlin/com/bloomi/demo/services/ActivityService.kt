package com.bloomi.demo.services

import com.bloomi.demo.exceptions.exceptions.ResourceNotFoundException
import com.bloomi.demo.mappers.ActivityMapper
import com.bloomi.demo.models.requests.CreateActivityRequest
import com.bloomi.demo.models.requests.UpdateActivityRequest
import com.bloomi.demo.models.responses.ActivityResponse
import com.bloomi.demo.repositories.ActivityRepository
import com.bloomi.demo.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val activityRepository: ActivityRepository,
    private val activityMapper: ActivityMapper,
    userRepository: UserRepository
) {

    fun createActivity(request: CreateActivityRequest): ActivityResponse {
        val activity = activityRepository.save(activityMapper.toEntity(request))
        return activityMapper.toResponse(activity)
    }

    fun getActivityById(id: String): ActivityResponse {
        val activity = activityRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Activity with ID $id not found")
        }
        return activityMapper.toResponse(activity)
    }

    fun getAllActivities(): List<ActivityResponse> {
        return activityRepository.findAll().map { activityMapper.toResponse(it) }
    }

    fun updateActivity(id: String, request: UpdateActivityRequest): ActivityResponse {
        val existing = activityRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Activity with ID $id not found")
        }

        val updated = existing.copy(
            title = request.title ?: existing.title,
            type = request.type?.name ?: existing.type,
            description = request.description ?: existing.description,
            location = request.location ?: existing.location,
            date = request.date ?: existing.date,
            foundationId = request.foundationId ?: existing.foundationId
        )

        return activityMapper.toResponse(activityRepository.save(updated))
    }

    fun deleteActivity(id: String) {
        if (!activityRepository.existsById(id)) {
            throw ResourceNotFoundException("Cannot delete: Activity with ID $id does not exist")
        }
        activityRepository.deleteById(id)
    }
}