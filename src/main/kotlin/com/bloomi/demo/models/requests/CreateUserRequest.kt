package com.bloomi.demo.models.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateUserRequest (
    val name: String,
    val email: String
)


