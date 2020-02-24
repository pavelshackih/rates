package io.pavel.shackih.rates.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulers {

    fun mainScheduler(): Scheduler

    fun ioScheduler(): Scheduler
}

class RxSchedulersImpl : RxSchedulers {

    override fun mainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun ioScheduler(): Scheduler = Schedulers.io()
}