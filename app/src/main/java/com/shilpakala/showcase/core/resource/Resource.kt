package com.shilpakala.showcase.core.resource

/**
 * Generic sealed class for wrapping data with loading, success, and error states.
 * Used across all layers to communicate state consistently.
 */
sealed class Resource<out T> {

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()

    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun errorMessageOrNull(): String? = when (this) {
        is Error -> message
        else -> null
    }

    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
        fun error(message: String, throwable: Throwable? = null): Resource<Nothing> =
            Error(message, throwable)
        fun loading(): Resource<Nothing> = Loading
    }
}

/**
 * Inline helper to execute a suspend block and wrap the result in Resource.
 */
suspend inline fun <T> safeCall(
    crossinline block: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(block())
    } catch (e: Exception) {
        Resource.Error(
            message = e.localizedMessage ?: "An unexpected error occurred",
            throwable = e
        )
    }
}
