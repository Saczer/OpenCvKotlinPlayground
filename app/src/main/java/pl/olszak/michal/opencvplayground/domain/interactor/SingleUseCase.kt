package pl.olszak.michal.opencvplayground.domain.interactor

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import pl.olszak.michal.opencvplayground.concurrent.SchedulersFacade

/**
 * Created by molszak.
 * 31.12.2017
 */
abstract class SingleUseCase<T, in Params> constructor(private val schedulersFacade: SchedulersFacade) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildObservable(params: Params? = null): Single<T>

    open fun execute(
            singleObserver: DisposableSingleObserver<T>,
            params: Params? = null) {

        val single = buildObservable(params)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.main()) as Single<T>

        addDisposable(single.subscribeWith(singleObserver))
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

}