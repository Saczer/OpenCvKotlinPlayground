package pl.olszak.michal.opencvplayground.di.module

import dagger.Module
import dagger.Provides
import pl.olszak.michal.opencvplayground.di.scope.PerActivity
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

}