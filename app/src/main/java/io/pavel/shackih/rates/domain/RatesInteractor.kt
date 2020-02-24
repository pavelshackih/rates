package io.pavel.shackih.rates.domain

import io.reactivex.Flowable
import java.math.BigDecimal

class RatesInteractor(private val ratesRepository: RatesRepository) {

    fun observeRates(baseRate: Rate): Flowable<List<Rate>> =
        ratesRepository.getRates(baseRate.code, INTERVAL_MS)
            .map { mapList(it, baseRate) }

    private fun mapList(
        map: Map<String, BigDecimal>,
        baseRate: Rate
    ): ArrayList<Rate> {
        val result = ArrayList<Rate>()
        for ((key, value) in map) {
            val rateValue = baseRate.value * value
            result.add(Rate(key, rateValue))
        }
        result.sortBy { it.code }
        result.add(0, baseRate)
        return result
    }

    companion object {
        private const val INTERVAL_MS = 1000L
    }
}
