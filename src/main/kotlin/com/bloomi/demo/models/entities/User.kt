package com.bloomi.demo.models.entities

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


///para modelos documentales cambiamos el @Entity por @Document
@Document(collection = "users")
data class User (
    @Id
    val id: String? = null,
    val name: String,
    val email: String,
    val joinedActivities: List<String> = emptyList()
)

