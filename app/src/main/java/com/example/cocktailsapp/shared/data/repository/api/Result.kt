package com.example.cocktailsapp.shared.data.repository.api

/**
 * Created by Mert on 2024
 */
sealed class Result<out T> {
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
}
