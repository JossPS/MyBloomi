package com.bloomi.demo.models.requests

import com.bloomi.demo.models.entities.ActivityType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class CreateActivityRequest(
    val title: String,
    val description: String,
    val type: ActivityType,
    @JsonProperty("date")
    val date: LocalDate,
    val location: String,
    @JsonProperty("foundationId")
    val foundationId: String
)