package com.aura.connection

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Pojo(
    @SerialName("id") val id: String,
    @SerialName("password") val password: String
)
