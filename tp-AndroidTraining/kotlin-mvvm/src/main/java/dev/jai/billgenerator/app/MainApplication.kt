package dev.jai.billgenerator.app

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dev.jai.billgenerator.di.component.DaggerAppComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


class MainApplication : Application(),
        HasActivityInjector {
    @Inject
    lateinit var mCalligraphyConfig: CalligraphyConfig

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    companion object Factory {
        val sInstance: MainApplication = MainApplication()
        fun getInstance(): MainApplication {
            return sInstance
        }
    }


    override fun onCreate() {
        super.onCreate()
        initializeComponent();
    }

    private fun initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        // Setting custom Font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(mCalligraphyConfig))
                .build())
    }
}