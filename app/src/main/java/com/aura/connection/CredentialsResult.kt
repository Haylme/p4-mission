import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CredentialsResult(
    @SerialName("granted") val granted: Boolean,


)
