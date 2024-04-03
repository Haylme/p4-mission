package com.aura

import com.aura.connection.BankCall
import com.aura.connection.BankService
import com.aura.model.Credentials
import com.aura.model.CredentialsResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import io.mockk.mockkObject

class LoginTest {
    private val mockBankService = mockk<BankService>()

    @Before
    fun setUp() {
        // Mock the BankService
        mockkObject(BankService.Companion)
        coEvery { BankService.retrofit.create(BankService::class.java) } returns mockBankService
    }

    @Test
    fun `fetchLogin with valid credentials returns success`() = runBlocking {
        // Arrange
        val id = "validUser"
        val password = "validPassword"
        val expectedResponse = CredentialsResult(granted = true)
        coEvery { mockBankService.getLogin(Credentials(id, password)) } returns Response.success(expectedResponse)

        // Act
        val result = BankCall.fetchLogin(id, password)

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `fetchLogin with null id throws IllegalArgumentException`() = runBlocking {
        var exception: Exception? = null
        try {
            // Act
            BankCall.fetchLogin(null, "password")
        } catch (e: Exception) {
            exception = e
        }

        // Assert
        assertTrue(exception is IllegalArgumentException)
    }

    @Test
    fun `fetchLogin with null password throws IllegalArgumentException`() = runBlocking {
        var exception: Exception? = null
        try {
            // Act
            BankCall.fetchLogin("id", null)
        } catch (e: Exception) {
            exception = e
        }

        // Assert
        assertTrue(exception is IllegalArgumentException)
    }
}
