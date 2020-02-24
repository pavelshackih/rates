package io.pavel.shackih.rates.domain

import java.math.BigDecimal

data class Rate(
    val code: String,
    var value: BigDecimal
)