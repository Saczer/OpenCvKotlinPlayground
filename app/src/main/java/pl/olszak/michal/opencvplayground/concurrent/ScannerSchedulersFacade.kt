package pl.olszak.michal.opencvplayground.concurrent

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by molszak.
 * 31.12.2017
 */
class ScannerSchedulersFacade @Inject constructor() : SchedulersFacade {

    override fun io(): Scheduler = Schedulers.io()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}