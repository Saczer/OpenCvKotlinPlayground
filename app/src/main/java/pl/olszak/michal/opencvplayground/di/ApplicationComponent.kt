package pl.olszak.michal.opencvplayground.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.olszak.michal.opencvplayground.OpenCvPlayground
import pl.olszak.michal.opencvplayground.di.module.ActivityBindingModule
import pl.olszak.michal.opencvplayground.di.module.ApplicationModule

/**
 * Created by molszak.
 * 31.12.2017
 */
@Component(
        modules = [
            ActivityBindingModule::class,
            ApplicationModule::class,
            AndroidSupportInjectionModule::class
        ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application) : Builder

        fun bind() : ApplicationComponent
    }

    fun inject(application: OpenCvPlayground)

}