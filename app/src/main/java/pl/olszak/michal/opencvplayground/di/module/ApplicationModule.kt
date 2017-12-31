package pl.olszak.michal.opencvplayground.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import org.opencv.android.OpenCVLoader
import pl.olszak.michal.opencvplayground.OpenCvPlayground
import pl.olszak.michal.opencvplayground.concurrent.ScannerSchedulersFacade
import pl.olszak.michal.opencvplayground.di.scope.PerApplication
import pl.olszak.michal.opencvplayground.util.PermissionManager
import timber.log.Timber

/**
 * Created by molszak.
 * 31.12.2017
 */
@Module
@PerApplication
class ApplicationModule {

    companion object {
        init {
            if(!OpenCVLoader.initDebug()){
                Timber.d("OpenCv did not load");
            }else{
                System.loadLibrary("detection-tracker")
            }
        }
    }

    @Provides
    fun provideContext(application: OpenCvPlayground) : Context = application

    @Provides
    fun providePermissionManager(manager : PermissionManager) = manager

    @Provides
    fun provideSchedulersFacade(schedulers : ScannerSchedulersFacade) = schedulers

}