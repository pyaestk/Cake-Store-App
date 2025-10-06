package com.example.shoppingapp.domain.util

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Response<T>(data)
    class Error<T>(message: String, data: T? = null): Response<T>(data, message)

    class Loading<T>: Response<T>()
}

inline fun <T, R> Response<T>.map(transform: (T) -> R): Response<R> {
    return when (this) {
        is Response.Success -> {
            data?.let { Response.Success(transform(it)) }
                ?: Response.Error("Empty data")
        }
        is Response.Error -> Response.Error(message ?: "Unknown error")
        is Response.Loading -> Response.Loading()
    }
}

