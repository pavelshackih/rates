package io.pavel.shackih.rates.domain

import io.reactivex.Flowable
import java.math.BigDecimal

interface RatesRepository {

    fun getRates(base: String, intervalInMs: Long): Flowable<Map<String, BigDecimal>>
}