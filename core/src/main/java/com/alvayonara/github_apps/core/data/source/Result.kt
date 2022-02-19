package com.alvayonara.github_apps.core.data.source

data class Result<out T>(
    val status: Status,
    val body: T? = null,
    val throwable: Throwable? = null
) {
    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun <T> loading(data: T? = null): Result<T> {
            return Result(
                Status.LOADING,
                body = data
            )
        }

        @JvmStatic
        fun <T> success(data: T?): Result<T> {
            return Result(
                Status.SUCCESS,
                body = data
            )
        }

        @JvmStatic
        @JvmOverloads
        fun <T> error(data: Throwable? = null): Result<T> {
            return Result(
                Status.ERROR,
                throwable = data
            )
        }
    }
}