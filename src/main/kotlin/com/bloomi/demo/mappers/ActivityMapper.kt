package com.bloomi.demo.mappers

import com.bloomi.demo.models.entities.Activity
import com.bloomi.demo.models.entities.ActivityType
import com.bloomi.demo.models.requests.CreateActivityRequest
import com.bloomi.demo.models.responses.ActivityResponse
import org.springframework.stereotype.Component


@Component
class ActivityMapper {

    fun toEntity(request: CreateActivityRequest): Activity {
        return Activity(
            title = request.title,
            type = request.type.name,
            description = request.description,
            location = request.location,
            date = request.date,
            foundationId = request.foundationId
        )
    }

    fun toResponse(activity: Activity): ActivityResponse {
        return ActivityResponse(
            id = activity.id ?: "",
            title = activity.title,
            type = ActivityType.valueOf(activity.type),
            description = activity.description,
            location = activity.location,
            date = activity.date,
            foundationId = activity.foundationId
        )
    }
}