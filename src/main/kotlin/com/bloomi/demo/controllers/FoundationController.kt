package com.bloomi.demo.controllers

import com.bloomi.demo.models.requests.CreateFoundationRequest
import com.bloomi.demo.models.requests.UpdateFoundationRequest
import com.bloomi.demo.models.responses.FoundationResponse
import com.bloomi.demo.routes.Routes
import com.bloomi.demo.services.FoundationService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(Routes.FOUNDATIONS_BASE + Routes.FOUNDATIONS)
class FoundationController(
    private val foundationService: FoundationService
) {
    @PostMapping
    fun createFoundation(@RequestBody request: CreateFoundationRequest): FoundationResponse {
        return foundationService.createFoundation(request)
    }

    @GetMapping("/{id}")
    fun getFoundationById(@PathVariable id: String): FoundationResponse {
        return foundationService.getFoundationById(id)
    }

    @GetMapping
    fun getAllFoundations(): List<FoundationResponse> {
        return foundationService.getAllFoundations()
    }

    @PutMapping("{id}")
    fun updateFoundation(@PathVariable id: String, @RequestBody request: UpdateFoundationRequest
    ): FoundationResponse{
        return foundationService.updateFoundation(id, request)
    }


    @DeleteMapping("/{id}")
    fun deleteFoundation(@PathVariable id: String) {
        foundationService.deleteFoundation(id)
    }
}
