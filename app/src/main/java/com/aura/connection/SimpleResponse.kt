package com.aura.connection

import retrofit2.Response

data class SimpleResponse<T>(


    val status: Status,

    val data: Response<T>?,

    val exeption: Exception?


) {

    sealed class Status {


        object Success : Status()

        object Failure : Status()


    }

    companion object {


        fun <T> success(data: Response<T>): SimpleResponse<T> {


            return SimpleResponse(


                status = Status.Success,
                data = data,
                exeption = null

            )

        }


        fun <T> failure(exeption: Exception?): SimpleResponse<T> {

            return SimpleResponse(

                status = Status.Failure,
                data = null,
                exeption = exeption


            )


        }


    }


    private val failed: Boolean
        get() = this.status == Status.Failure

    val successful: Boolean
        get() = !failed && this.data?.isSuccessful == true

    val body1: T
        get() = this.data!!.body()!!


}