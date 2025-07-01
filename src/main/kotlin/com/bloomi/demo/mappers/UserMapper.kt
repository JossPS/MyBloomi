package com.bloomi.demo.mappers

import com.bloomi.demo.models.entities.User
import com.bloomi.demo.models.requests.CreateUserRequest
import com.bloomi.demo.models.responses.UserResponse
import org.springframework.stereotype.Component


@Component
class UserMapper {
    fun toEntity(request: CreateUserRequest): User{
        return User(
            name =request.name,
            email = request.email
        )
    }

    fun toResponse(user: User): UserResponse{
        return UserResponse(
            id = user.id ?: "",
            name = user.name,
            email = user.email,
            joinedActivities = user.joinedActivities
        )
    }
}