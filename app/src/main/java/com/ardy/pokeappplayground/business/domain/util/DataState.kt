package com.ardy.pokeappplayground.business.datasource.network

data class DataState<T>(
    val error: String? = null,
    val data: T? = null,
    val loading: Boolean = false
) {

    companion object {

        fun <T> error(message: String): DataState<T> {
            return DataState(error = message)
        }

        fun <T> data(data: T): DataState<T> {
            return DataState(data = data)
        }

        fun <T> loading(): DataState<T> = DataState(loading = true)
    }
}