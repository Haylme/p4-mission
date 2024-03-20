package com.aura.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Credentials(
    @SerialName("id") val id: String,
    @SerialName("password") val password: String
)
