package com.bloomi.demo.controllers

import com.bloomi.demo.models.requests.CreateActivityRequest
import com.bloomi.demo.models.requests.UpdateActivityRequest
import com.bloomi.demo.models.responses.ActivityResponse
import com.bloomi.demo.routes.Routes
import com.bloomi.demo.services.ActivityService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(Routes.ACTIVITIES_BASE + Routes.ACTIVITIES)
class ActivityController(
    private val activityService: ActivityService
) {

    @PostMapping
    fun createActivity(@RequestBody request: CreateActivityRequest): ActivityResponse {
        return activityService.createActivity(request)
    }

    @GetMapping("/{id}")
    fun getActivityById(@PathVariable id: String): ActivityResponse {
        return activityService.getActivityById(id)
    }

    @GetMapping
    fun getAllActivities(): List<ActivityResponse> {
        return activityService.getAllActivities()
    }

    @PutMapping("/{id}")
    fun updateActivity(@PathVariable id: String, @RequestBody request: UpdateActivityRequest
    ): ActivityResponse {
        return activityService.updateActivity(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteActivity(@PathVariable id: String) {
        activityService.deleteActivity(id)
    }
}