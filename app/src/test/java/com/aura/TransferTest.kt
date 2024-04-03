package com.aura

import com.aura.connection.BankCall
import com.aura.connection.BankService
import com.aura.model.Transfer
import com.aura.model.TransferResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import org.junit.Assert.*

class TransferTest {


    private val mockBankService = mockk<BankService>()

    @Before
    fun setup(){

        mockkObject(BankService.Companion)
        coEvery { BankService.retrofit.create(BankService::class.java) }returns mockBankService


    }

    @Test
    fun `fetchtransfer with valid input`() = runBlocking{

        val sender = "1234"
        val recipient = "5678"
        val amount = 500.00

        val transferRequest = Transfer(sender, recipient, amount)
        val expectedResponse = TransferResult(result = true)

        coEvery { mockBankService.getTransfer(transferRequest) } returns Response.success(expectedResponse)


        mockkObject(BankCall)
        coEvery { BankCall.fetchTransfer(sender, recipient, amount) } returns expectedResponse


        val result = BankCall.fetchTransfer(sender, recipient, amount)


        assertEquals(expectedResponse, result)

    }

}