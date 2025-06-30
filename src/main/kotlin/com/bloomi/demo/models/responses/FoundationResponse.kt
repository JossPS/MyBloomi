package com.bloomi.demo.models.responses

import com.bloomi.demo.models.entities.BankAccount

class FoundationResponse (
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val bankAccount: BankAccountResponse
)
data class BankAccountResponse(
    val bankName: String,
    val accountType: String,
    val accountNumber: String
)