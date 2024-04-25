package com.raven.home.data

sealed class Result<T> {
    data class Success<T>(val body: T?) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
}