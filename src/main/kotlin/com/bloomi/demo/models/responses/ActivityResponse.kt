package com.bloomi.demo.models.responses

import com.bloomi.demo.models.entities.ActivityType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ActivityResponse(
    val id: String,
    val title: String,
    val type: ActivityType,
    val description: String,
    val location: String,
    val date: LocalDate,
    @JsonProperty("foundation_id")
    val foundationId: String
)