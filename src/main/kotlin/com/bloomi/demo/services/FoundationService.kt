package com.bloomi.demo.services

import com.bloomi.demo.exceptions.exceptions.ResourceNotFoundException
import com.bloomi.demo.mappers.FoundationMapper
import com.bloomi.demo.models.entities.Foundation
import com.bloomi.demo.models.requests.CreateFoundationRequest
import com.bloomi.demo.models.requests.UpdateFoundationRequest
import com.bloomi.demo.models.responses.FoundationResponse
import com.bloomi.demo.repositories.FoundationRepository
import org.springframework.stereotype.Service


@Service
class FoundationService(
    private val foundationRepository: FoundationRepository,
    private val foundationMapper: FoundationMapper
){
    fun createFoundation(request: CreateFoundationRequest): FoundationResponse {
        val foundation = foundationRepository.save(foundationMapper.toEntity(request))
        return foundationMapper.toResponse(foundation)
    }
    fun getFoundationById(id: String): FoundationResponse {
        val foundation = foundationRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Foundation with ID $id not found")
        }
        return foundationMapper.toResponse(foundation)
    }

    fun getAllFoundations(): List<FoundationResponse> {
        return foundationRepository.findAll().map { foundationMapper.toResponse(it) }
    }

    fun deleteFoundation(id: String) {
        if (!foundationRepository.existsById(id)) {
            throw ResourceNotFoundException("Can not delete: Foundation with ID $id does not exist")
        }
        foundationRepository.deleteById(id)
    }

    fun updateFoundation(id: String, request: UpdateFoundationRequest): FoundationResponse {
        val existing = foundationRepository.findById(id).orElseThrow{
            ResourceNotFoundException("Foundation with id $id not found")
        }
        val updated = existing.copy(
            name = request.name ?: existing.name,
            email = request.email ?: existing.email,
            phone = request.phone ?: existing.phone,
            bankAccount = existing.bankAccount.copy(
                bankName = request.bankName ?: existing.bankAccount.bankName,
                accountType = request.accountType ?: existing.bankAccount.accountType,
                accountNumber = request.accountNumber ?: existing.bankAccount.accountNumber
            )
        )

        return foundationMapper.toResponse(foundationRepository.save(updated))
    }
}