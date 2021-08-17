package com.ardy.pokeappplayground.business.datasource.network

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.HttpsURLConnection

object NetworkErrorType {

    const val API_STATUS_CODE_LOCAL_ERROR = 0

    const val ERROR_HTTP_BAD_REQUEST_400 = "ERROR_HTTP_BAD_REQUEST_400"
    const val ERROR_HTTP_UNAUTHORIZED_401 = "ERROR_HTTP_UNAUTHORIZED_401"
    const val ERROR_HTTP_FORBIDDEN_403 = "ERROR_HTTP_FORBIDDEN_403"
    const val ERROR_HTTP_INTERNAL_ERROR_500 = "ERROR_HTTP_INTERNAL_ERROR_500"
    const val ERROR_NO_INTERNET_CONNECTION = "ERROR_NO_INTERNET_CONNECTION"
    const val ERROR_TIMEOUT = "ERROR_TIMEOUT"
    const val ERROR_JSON_PARSING = "ERROR_JSON_PARSING"
    const val ERROR_JSON_MALFORMED = "ERROR_JSON_MALFORMED"
    const val ERROR_GENERAL = "ERROR_GENERAL"

}

fun Throwable.toResponseErrorDetail(): String {
    when (this) {
        is HttpException -> {
            return this.toErrorDetail()
        }
        is IOException -> {
            return this.toErrorDetail()
        }
        is JsonParseException -> {
            return NetworkErrorType.ERROR_JSON_PARSING
        }
        else -> {
            return if (this.message.isNullOrBlank()) NetworkErrorType.ERROR_GENERAL else this.message.orEmpty()
        }
    }
}

fun HttpException.toErrorDetail(): String {
    val errorMessage: String

    when (this.code()) {
        HttpsURLConnection.HTTP_UNAUTHORIZED -> {
            errorMessage = NetworkErrorType.ERROR_HTTP_UNAUTHORIZED_401
        }
        HttpsURLConnection.HTTP_FORBIDDEN -> {
            errorMessage = NetworkErrorType.ERROR_HTTP_FORBIDDEN_403
        }
        HttpsURLConnection.HTTP_INTERNAL_ERROR -> {
            errorMessage = NetworkErrorType.ERROR_HTTP_INTERNAL_ERROR_500
        }
        HttpsURLConnection.HTTP_BAD_REQUEST -> {
            errorMessage = NetworkErrorType.ERROR_HTTP_BAD_REQUEST_400
        }
        NetworkErrorType.API_STATUS_CODE_LOCAL_ERROR -> {
            errorMessage = NetworkErrorType.ERROR_NO_INTERNET_CONNECTION
        }
        else -> {
            errorMessage = this.localizedMessage ?: this.message()
        }
    }

    return errorMessage
}

fun IOException.toErrorDetail(): String {
    return when (this) {
        is SocketTimeoutException -> {
            NetworkErrorType.ERROR_TIMEOUT
        }
        is MalformedJsonException -> {
            NetworkErrorType.ERROR_JSON_MALFORMED
        }
        else -> {
            NetworkErrorType.ERROR_NO_INTERNET_CONNECTION
        }
    }
}