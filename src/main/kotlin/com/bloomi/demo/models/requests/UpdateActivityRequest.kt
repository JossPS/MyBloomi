package com.bloomi.demo.models.requests

import com.bloomi.demo.models.entities.ActivityType
import java.time.LocalDate

data class UpdateActivityRequest(
    val title: String? = null,
    val type: ActivityType? = null,
    val description: String? = null,
    val location: String? = null,
    val date: LocalDate? = null,
    val foundationId: String? = null
)