package com.codingchallenge.nycschools.data.remote

// Sealed call from netwrok response
sealed class Results<out T : Any> {
    data class Success<out T : Any>(val data: T) : Results<T>()
    data class Error(val throwable: Throwable) : Results<Nothing>()
}