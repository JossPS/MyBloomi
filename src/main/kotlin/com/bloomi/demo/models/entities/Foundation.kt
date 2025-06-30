package com.bloomi.demo.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "foundation")
data class Foundation (
    @Id
    val id: String? = null,
    val name: String,
    val email: String,
    val phone: String,
    val bankAccount: BankAccount
)
data class BankAccount(
    val bankName: String,
    val accountType: String,
    val accountNumber: String
)