package com.bloomi.demo.controllers

import com.bloomi.demo.models.requests.CreateUserRequest
import com.bloomi.demo.models.responses.ActivityResponse
import com.bloomi.demo.models.responses.UserResponse
import com.bloomi.demo.routes.Routes
import com.bloomi.demo.services.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(Routes.USERS_BASE + Routes.USERS)
class UserController (
    private val userService: UserService
){
    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): UserResponse{
        return userService.createUser(request)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): UserResponse{
        return userService.getUserById(id)
    }

    @GetMapping
    fun getAllUsers(): List<UserResponse>{
        return userService.getAllUsers()
    }


    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String){
        userService.deleteUser(id)
    }

    @PostMapping(Routes.JOIN_ACTIVITY)
    fun joinActivity(@PathVariable userId: String, @PathVariable activityId: String): UserResponse {
        return userService.joinActivity(userId, activityId)
    }

    @GetMapping(Routes.USER_ACTIVITIES)
    fun getUserActivities(@PathVariable userId: String): List<ActivityResponse> {
        return userService.getUserActivities(userId)
    }
}