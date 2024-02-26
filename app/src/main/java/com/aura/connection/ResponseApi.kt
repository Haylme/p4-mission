import com.aura.connection.Pojo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseApi(
    @SerialName("granted") val granted: Boolean


)
