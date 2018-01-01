package pl.olszak.michal.opencvplayground.di.module

import dagger.Module
import dagger.Provides
import pl.olszak.michal.opencvplayground.concurrent.SchedulersFacade
import pl.olszak.michal.opencvplayground.di.scope.PerActivity
import pl.olszak.michal.opencvplayground.domain.interactor.scanner.ScanFrame
import pl.olszak.michal.opencvplayground.domain.scanner.CameraFrameScanner
import pl.olszak.michal.opencvplayground.domain.scanner.FrameScanner
import pl.olszak.michal.opencvplayground.scanner.ScannerActivity

/**
 * Created by molszak.
 * 31.12.2017
 */
@Module
@PerActivity
class ScannerActivityModule{

    @Provides
    fun provideScannerActivity(activity: ScannerActivity) : ScannerActivity = activity

    @Provides
    fun provideFrameScanner(frameScanner: CameraFrameScanner) : FrameScanner = frameScanner

    @Provides
    fun provideScaneFrame(schedulersFacade: SchedulersFacade, frameScanner: FrameScanner) : ScanFrame{
        return ScanFrame(schedulersFacade, frameScanner)
    }

}