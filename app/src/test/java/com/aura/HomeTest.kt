package com.aura

import com.aura.connection.BankService
import com.aura.model.Account
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import org.junit.Assert.*

class HomeTest {


    private val mockBankService = mockk<BankService>()


    @Before
    fun setup() {

        mockkObject(BankService.Companion)
        coEvery { BankService.retrofit.create(BankService::class.java) } returns mockBankService

    }

    @Test
    fun `fetchAccount is not null or empty`() = runBlocking {

        val id = "valid id"
        val account = Account(id, true, 1000.00)

        coEvery { mockBankService.getAccount(id) } returns Response.success(listOf(account))

        val response = mockBankService.getAccount(id)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertTrue(response.body()?.isNotEmpty() == true)


    }


}