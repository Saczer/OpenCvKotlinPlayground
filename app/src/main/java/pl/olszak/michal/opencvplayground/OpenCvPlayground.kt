package pl.olszak.michal.opencvplayground

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import pl.olszak.michal.opencvplayground.di.ApplicationComponent
import pl.olszak.michal.opencvplayground.di.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * @author molszak
 *         created on 13.12.2017.
 */
class OpenCvPlayground : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        val component : ApplicationComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .bind()

        component.inject(this)

        plantTimber()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }
}