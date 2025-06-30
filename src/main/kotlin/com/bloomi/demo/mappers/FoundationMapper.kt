package com.bloomi.demo.mappers

import com.bloomi.demo.models.entities.BankAccount
import com.bloomi.demo.models.entities.Foundation
import com.bloomi.demo.models.requests.CreateFoundationRequest
import com.bloomi.demo.models.responses.BankAccountResponse
import com.bloomi.demo.models.responses.FoundationResponse
import org.springframework.stereotype.Component


@Component
class FoundationMapper {

    fun toEntity(request: CreateFoundationRequest): Foundation {
        return Foundation(
            name = request.name,
            email = request.email,
            phone = request.phone,
            bankAccount = BankAccount(
                bankName = request.bankName,
                accountType = request.accountType,
                accountNumber = request.accountNumber
            )
        )
    }

    fun toResponse(foundation: Foundation): FoundationResponse {
        return FoundationResponse(
            id = foundation.id ?: "",
            name = foundation.name,
            email = foundation.email,
            phone = foundation.phone,
            bankAccount = BankAccountResponse(
                bankName = foundation.bankAccount.bankName,
                accountType = foundation.bankAccount.accountType,
                accountNumber = foundation.bankAccount.accountNumber
            )
        )
    }
}