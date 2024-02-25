import com.aura.connection.Pojo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseApi(
    @SerialName("granted") val granted: Boolean


) : List<Pojo> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: Pojo): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<Pojo>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): Pojo {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: Pojo): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<Pojo> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: Pojo): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<Pojo> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<Pojo> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<Pojo> {
        TODO("Not yet implemented")
    }
}
