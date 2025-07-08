package com.bloomi.demo

import com.bloomi.demo.exceptions.exceptions.ResourceNotFoundException
import com.bloomi.demo.mappers.FoundationMapper
import com.bloomi.demo.models.entities.BankAccount
import com.bloomi.demo.models.entities.Foundation
import com.bloomi.demo.models.requests.CreateFoundationRequest
import com.bloomi.demo.models.requests.UpdateFoundationRequest
import com.bloomi.demo.repositories.FoundationRepository
import com.bloomi.demo.services.FoundationService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

class FoundationServiceTest {

    private val repository = mock(FoundationRepository::class.java)
    private val mapper = FoundationMapper()
    private val service = FoundationService(repository, mapper)

    @Test
    fun should_create_a_foundation() {
        val request = CreateFoundationRequest(
            name = "EcoVida",
            email = "contacto@ecovida.org",
            phone = "0987654321",
            bankName = "Banco Pichincha",
            accountType = "Ahorros",
            accountNumber = "1234567890"
        )

        val savedFoundation = mapper.toEntity(request).copy(id = "f1")

        `when`(repository.save(any())).thenReturn(savedFoundation)

        val result = service.createFoundation(request)

        assertEquals("EcoVida", result.name)
        assertEquals("contacto@ecovida.org", result.email)
        assertEquals("f1", result.id)
    }

    @Test
    fun should_return_a_foundation_by_id() {
        val foundation = Foundation(
            id = "f2",
            name = "Reverdecer",
            email = "info@reverdecer.org",
            phone = "0999999999",
            bankAccount = BankAccount("Banco Guayaquil", "Corriente", "9988776655")
        )

        `when`(repository.findById("f2")).thenReturn(Optional.of(foundation))

        val result = service.getFoundationById("f2")

        assertEquals("Reverdecer", result.name)
        assertEquals("Banco Guayaquil", result.bankAccount.bankName)
    }

    @Test
    fun should_throw_exception_when_foundation_not_found() {
        `when`(repository.findById("f3")).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            service.getFoundationById("f3")
        }
    }

    @Test
    fun should_return_all_foundations() {
        val foundations = listOf(
            Foundation(
                id = "f1", name = "Vida Verde", email = "vidav@a.com", phone = "0999999999",
                bankAccount = BankAccount("B1", "Ahorros", "111")
            ),
            Foundation(
                id = "f2", name = "Bosque Limpio", email = "bosquel@b.com", phone = "0985687865",
                bankAccount = BankAccount("B2", "Corriente", "222")
            )
        )

        `when`(repository.findAll()).thenReturn(foundations)

        val result = service.getAllFoundations()

        assertEquals(2, result.size)
        assertEquals("Vida Verde", result[0].name)
    }

    @Test
    fun should_delete_foundation_by_id() {
        `when`(repository.existsById("f1")).thenReturn(true)

        service.deleteFoundation("f1")

        verify(repository).deleteById("f1")
    }

    @Test
    fun should_throw_exception_when_deleting_nonexistent_foundation() {
        `when`(repository.existsById("fX")).thenReturn(false)

        assertThrows<ResourceNotFoundException> {
            service.deleteFoundation("fX")
        }
    }

    @Test
    fun should_update_foundation() {
        val existing = Foundation(
            id = "f1",
            name = "Fundación Tree",
            email = "Listatree@email.com",
            phone = "1111",
            bankAccount = BankAccount("Banco A", "Ahorros", "000")
        )

        val request = UpdateFoundationRequest(
            name = "Fundación Nueva vida",
            email = "nuevafunda@email.com",
            phone = null,
            bankName = "Banco B",
            accountType = null,
            accountNumber = "12345"
        )

        `when`(repository.findById("f1")).thenReturn(Optional.of(existing))
        `when`(repository.save(any())).thenAnswer { it.getArgument(0) }

        val result = service.updateFoundation("f1", request)

        assertEquals("Fundación Nueva", result.name)
        assertEquals("nuevafunda@email.com", result.email)
        assertEquals("Banco B", result.bankAccount.bankName)
        assertEquals("Ahorros", result.bankAccount.accountType)
        assertEquals("12345", result.bankAccount.accountNumber)
    }
}
