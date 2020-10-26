package dev.jai.billgenerator.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dev.jai.billgenerator.app.MainApplication
import dev.jai.billgenerator.data.AppDataManager
import dev.jai.billgenerator.di.module.ActivityModule
import dev.jai.billgenerator.di.module.AppModule
import dev.jai.billgenerator.utils.rx.SchedulerProvider
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ActivityModule::class])
interface AppComponent {
    fun inject(app: MainApplication)

    fun getDataManager(): AppDataManager?

    fun getSchedulerProvider(): SchedulerProvider?

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}