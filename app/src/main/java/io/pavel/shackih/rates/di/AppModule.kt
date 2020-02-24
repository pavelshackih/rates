package io.pavel.shackih.rates.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.pavel.shackih.rates.BuildConfig
import io.pavel.shackih.rates.data.RatesRepositoryImpl
import io.pavel.shackih.rates.data.network.RatesService
import io.pavel.shackih.rates.data.util.DateDeserializer
import io.pavel.shackih.rates.domain.RatesInteractor
import io.pavel.shackih.rates.domain.RatesRepository
import io.pavel.shackih.rates.rx.RxSchedulers
import io.pavel.shackih.rates.rx.RxSchedulersImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
object AppModule {

    private const val BASE_URL = "https://hiring.revolut.codes/api/android/"

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(interceptor)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()

    @Singleton
    @Provides
    fun providesRatesService(okHttpClient: OkHttpClient, gson: Gson): RatesService =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(RatesService::class.java)

    @Singleton
    @Provides
    fun providesRxSchedulers(): RxSchedulers = RxSchedulersImpl()

    @Singleton
    @Provides
    fun providesRatesRepository(ratesService: RatesService): RatesRepository =
        RatesRepositoryImpl(ratesService)

    @Singleton
    @Provides
    fun providesRatesInteractor(ratesRepository: RatesRepository): RatesInteractor =
        RatesInteractor(ratesRepository)
}
