package io.pavel.shackih.rates.di

import dagger.Component
import io.pavel.shackih.rates.domain.RatesInteractor
import io.pavel.shackih.rates.rx.RxSchedulers
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun rxSchedulers(): RxSchedulers

    fun ratesInteractor(): RatesInteractor
}
