package pl.olszak.michal.opencvplayground.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.olszak.michal.opencvplayground.scanner.ScannerActivity

/**
 * Created by molszak.
 * 31.12.2017
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [(ScannerActivityModule::class)])
    abstract fun bindMainActivity(): ScannerActivity

}