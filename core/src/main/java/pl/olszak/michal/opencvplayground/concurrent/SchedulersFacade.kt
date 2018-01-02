package pl.olszak.michal.opencvplayground.concurrent

import io.reactivex.Scheduler

/**
 * @author molszak
 *         created on 02.01.2018.
 */
interface SchedulersFacade {

    fun io(): Scheduler

    fun main(): Scheduler

}