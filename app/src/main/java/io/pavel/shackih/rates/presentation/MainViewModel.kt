package io.pavel.shackih.rates.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.pavel.shackih.rates.domain.Rate
import io.pavel.shackih.rates.domain.RatesInteractor
import io.pavel.shackih.rates.rx.RxSchedulers
import io.reactivex.disposables.Disposable
import java.math.BigDecimal

class MainViewModel(
    private val rxSchedulers: RxSchedulers,
    private val ratesInteractor: RatesInteractor
) : ViewModel(), DefaultLifecycleObserver {

    private val _rates = MutableLiveData<List<Rate>>()
    private val _error = MutableLiveData<Throwable>()
    private var disposable: Disposable? = null
    private var lastBase = EURO_RATE

    val rates: LiveData<List<Rate>>
        get() = _rates

    val error: LiveData<Throwable>
        get() = _error

    init {
        startObserveRates()
    }

    fun onChangeBase(rate: Rate) {
        startObserveRates(rate)
    }

    private fun startObserveRates(rate: Rate = EURO_RATE) {
        disposable?.dispose()
        disposable = ratesInteractor.observeRates(rate)
            .subscribeOn(rxSchedulers.ioScheduler())
            .observeOn(rxSchedulers.mainScheduler())
            .subscribe(
                { _rates.value = it },
                { _error.value = it }
            )
    }

    override fun onStart(owner: LifecycleOwner) {
        startObserveRates(lastBase)
    }

    override fun onStop(owner: LifecycleOwner) {
        disposable?.dispose()
    }

    override fun onCleared() {
        disposable?.dispose()
    }

    companion object {
        private val EURO_RATE = Rate("EUR", BigDecimal.TEN)
    }
}