package io.pavel.shackih.rates.data

import io.pavel.shackih.rates.data.network.RatesService
import io.pavel.shackih.rates.domain.RatesRepository
import io.reactivex.Flowable
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class RatesRepositoryImpl(
    private val ratesService: RatesService
) : RatesRepository {

    override fun getRates(base: String, intervalInMs: Long): Flowable<Map<String, BigDecimal>> {
        return Flowable.interval(0, intervalInMs, TimeUnit.MILLISECONDS)
            .onBackpressureDrop()
            .flatMap { ratesService.getLatest(base).map { it.rates }.toFlowable() }
    }
}
