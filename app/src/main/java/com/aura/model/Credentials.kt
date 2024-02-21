package com.aura.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Credentials(
    @SerialName("id") val id: String,
    @SerialName("password") val password: String
)