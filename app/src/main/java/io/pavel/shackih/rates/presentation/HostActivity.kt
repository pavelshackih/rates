package io.pavel.shackih.rates.presentation

interface HostActivity {

    fun onError(throwable: Throwable)

    fun onRetry()
}