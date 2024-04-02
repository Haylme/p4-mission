package com.aura

import LoginViewModel
import com.aura.connection.BankCall
import com.aura.model.CredentialsResult
import com.aura.model.SimpleResponse
import io.mockk.coEvery
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginTest {
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        // Mock the BankCall object
        mockkObject(BankCall)

        // Define the behavior for the fetchLogin method on the BankCall object
        coEvery { BankCall.fetchLogin("validUser", "validPassword") } returns CredentialsResult(
            granted = true
        )
        coEvery {
            BankCall.fetchLogin(
                not("validUser"),
                not("validPassword")
            )
        } returns CredentialsResult(granted = false)


        viewModel = LoginViewModel()
    }

    @Test
    fun `check login credentials with valid input`() = runTest {
        // Arrange
        val validId = "validUser"
        val validPassword = "validPassword"

        // Act
        viewModel.checkLoginCredentials(validId, validPassword)

        // Wait for the coroutine launched by checkLoginCredentials to complete
        advanceUntilIdle()

        // Assert
        val loginState = viewModel.loginEnabled.value
        assertEquals(SimpleResponse(SimpleResponse.Status.Success, true, null), loginState)
    }

    @Test
    fun `check login credentials with invalid input`() = runTest {
        // Arrange
        val invalidId = "invalidUser"
        val invalidPassword = "invalidPassword"

        // Act
        viewModel.checkLoginCredentials(invalidId, invalidPassword)

        // Wait for the coroutine launched by checkLoginCredentials to complete
        advanceUntilIdle()

        // Assert
        val loginState = viewModel.loginEnabled.value
        assert(loginState.status is SimpleResponse.Status.Failure)
    }
}
