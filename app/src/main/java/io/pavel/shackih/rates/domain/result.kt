package io.pavel.shackih.rates.domain

sealed class AppResult

data class OkResult(val list: List<Rate>, val fromLocal: Boolean = false) : AppResult()

data class ErrorResult(val reason: Throwable, val message: String = "") : AppResult()

