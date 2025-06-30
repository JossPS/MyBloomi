package com.bloomi.demo.models.requests

class CreateFoundationRequest (
    val name: String,
    val email: String,
    val phone: String,
    val bankName: String,
    val accountType: String,
    val accountNumber: String
)