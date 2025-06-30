package com.bloomi.demo.models.requests

data class UpdateFoundationRequest (
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val bankName: String? = null,
    val accountType: String? = null,
    val accountNumber: String? = null
)