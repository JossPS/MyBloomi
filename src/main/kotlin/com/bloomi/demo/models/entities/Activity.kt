package com.bloomi.demo.models.entities

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate


@Document(collection = "activities")
data class Activity(
    @Id
    val id: String? = null,
    val title: String,
    val type: ActivityType,
    val description: String,
    val location: String,
    val date: LocalDate,
    val foundationId: String
)

enum class ActivityType(val value: String) {
    MAINTENANCE("mantenimiento"),
    REFORESTATION("reforestación");

    companion object {
        @JsonCreator
        fun fromValue(value: String): ActivityType {
            return entries.firstOrNull { it.value.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid ActivityType: $value")
        }
    }
}