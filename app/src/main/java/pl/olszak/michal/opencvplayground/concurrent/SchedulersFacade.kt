package pl.olszak.michal.opencvplayground.concurrent

import io.reactivex.Scheduler

/**
 * Created by molszak.
 * 31.12.2017
 */
interface SchedulersFacade {

    fun io(): Scheduler

    fun main(): Scheduler

}