package com.bloomi.demo

import com.bloomi.demo.mappers.FoundationMapper
import com.bloomi.demo.models.entities.BankAccount
import com.bloomi.demo.models.entities.Foundation
import com.bloomi.demo.models.requests.CreateFoundationRequest
import kotlin.test.Test
import kotlin.test.assertEquals

class FoundationMapperTest {

    private val mapper = FoundationMapper()

    @Test
    fun should_map_request_to_entity() {
        val request = CreateFoundationRequest(
            name = "Fundación Vida",
            email = "vida@example.com",
            phone = "0999999999",
            bankName = "Banco Pichincha",
            accountType = "Corriente",
            accountNumber = "1234567890"
        )

        val result = mapper.toEntity(request)

        assertEquals("Fundación Vida", result.name)
        assertEquals("vida@example.com", result.email)
        assertEquals("0999999999", result.phone)
        assertEquals("Banco Pichincha", result.bankAccount.bankName)
        assertEquals("Corriente", result.bankAccount.accountType)
        assertEquals("1234567890", result.bankAccount.accountNumber)
    }

    @Test
    fun should_map_entity_to_response() {
        val foundation = Foundation(
            id = "f123",
            name = "Fundación Esperanza",
            email = "esperanza@example.com",
            phone = "0988888888",
            bankAccount = BankAccount(
                bankName = "Banco Guayaquil",
                accountType = "Ahorros",
                accountNumber = "9876543210"
            )
        )

        val response = mapper.toResponse(foundation)

        assertEquals("f123", response.id)
        assertEquals("Fundación Esperanza", response.name)
        assertEquals("esperanza@example.com", response.email)
        assertEquals("0988888888", response.phone)
        assertEquals("Banco Guayaquil", response.bankAccount.bankName)
        assertEquals("Ahorros", response.bankAccount.accountType)
        assertEquals("9876543210", response.bankAccount.accountNumber)
    }
}
