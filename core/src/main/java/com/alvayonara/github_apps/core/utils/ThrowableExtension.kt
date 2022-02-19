package com.alvayonara.github_apps.core.utils

import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

fun Throwable.getThrowable(): String {
    var messages = ""
    when (this) {
        is HttpException -> {
            when (this.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> messages = this.code().toString()
                HttpsURLConnection.HTTP_NOT_FOUND -> messages = this.code().toString()
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> messages = this.code().toString()
                HttpsURLConnection.HTTP_BAD_REQUEST -> messages = this.code().toString()
                HttpsURLConnection.HTTP_FORBIDDEN -> messages = this.code().toString()
                HttpsURLConnection.HTTP_CONFLICT -> messages = this.code().toString()
                HttpsURLConnection.HTTP_BAD_GATEWAY -> messages = this.code().toString()
            }
        }
        is UnknownHostException -> messages = "Unknown Error"
        is ConnectException -> messages = "No internet connected"
        is SocketTimeoutException -> messages = "No internet connected"
        is InterruptedIOException -> messages = "Request timeout"
        is IOException -> messages = this.message.toString()
        else -> message.toString()
    }
    return messages
}