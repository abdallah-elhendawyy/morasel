package com.example.morasel.util

sealed class Rescourseee<T> (
    val data: T? = null,
    val message: String? = null
    ) {
        class Success<T>(data: T) : Rescourseee<T>(data)
        class Error<T>(message: String, data: T? = null) : Rescourseee<T>(data, message)
        class Loading<T> : Rescourseee<T>()
}