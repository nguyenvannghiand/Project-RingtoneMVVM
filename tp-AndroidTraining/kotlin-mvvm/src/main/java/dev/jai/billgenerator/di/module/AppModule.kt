package dev.jai.billgenerator.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dev.jai.billgenerator.R
import dev.jai.billgenerator.data.AppDataManager
import dev.jai.billgenerator.data.DataManager
import dev.jai.billgenerator.data.local.pref.AppPreferencesHelper
import dev.jai.billgenerator.data.local.pref.PreferencesHelper
import dev.jai.billgenerator.data.remote.ApiHelper
import dev.jai.billgenerator.data.remote.AppApiHelper
import dev.jai.billgenerator.di.PreferenceInfo
import dev.jai.billgenerator.ui.fragment.basket.BasketAdapter
import dev.jai.billgenerator.utils.PREF_NAME
import dev.jai.billgenerator.utils.rx.AppSchedulerProvider
import dev.jai.billgenerator.utils.rx.SchedulerProvider
import io.github.inflationx.calligraphy3.CalligraphyConfig
import javax.inject.Singleton


@Module()
internal object AppModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SanFranciscoDisplay-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
    }

    @Provides
    @JvmStatic
    fun provideBasketAdapter(): BasketAdapter {
        return BasketAdapter()
    }

    @Provides
    @JvmStatic
    @PreferenceInfo
    fun providePreferenceName(): String {
        return PREF_NAME
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    // Provider process thread
    @Provides
    @JvmStatic
    fun provideSchedulerProvider() : SchedulerProvider {
        return AppSchedulerProvider();
    }
}