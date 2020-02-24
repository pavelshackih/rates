package io.pavel.shackih.rates

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import io.pavel.shackih.rates.di.AppComponent
import io.pavel.shackih.rates.di.DaggerAppComponent

class App : Application() {

    private lateinit var _component: AppComponent

    val appComponent: AppComponent
        get() = _component

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }

        _component = DaggerAppComponent.builder()
            .build()
    }
}
