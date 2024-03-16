import retrofit2.HttpException

data class SimpleResponse<T>(
    val status: Status,
    val data: T? = null,
    val exception: Exception? = null
) {
    sealed class Status {
        object Success : Status()
        object Failure : Status()
        object Initial : Status() // Added to represent the initial state
    }

    companion object {
        fun <T> success(data: T): SimpleResponse<T> {
            return SimpleResponse(status = Status.Success, data = data)
        }

        fun <T> failure(exception: Exception): SimpleResponse<T> {
            return SimpleResponse(status = Status.Failure, exception = exception)
        }

        fun <T> initial(): SimpleResponse<T> {
            return SimpleResponse(status = Status.Initial)
        }
    }

    /** val isSuccessful: Boolean
    get() = this.status == Status.Success

    val isFailed: Boolean
    get() = this.status == Status.Failure

    val isInitial: Boolean
    get() = this.status == Status.Initial**/
}