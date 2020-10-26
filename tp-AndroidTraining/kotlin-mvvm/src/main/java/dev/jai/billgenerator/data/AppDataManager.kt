package dev.jai.billgenerator.data

import android.content.Context
import dev.jai.billgenerator.data.local.pref.PreferencesHelper
import dev.jai.billgenerator.data.remote.ApiHelper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    context: Context,
    preferencesHelper: PreferencesHelper,
    apiHelper: ApiHelper
) : DataManager {
    var mApiHelper: ApiHelper = apiHelper

    var mContext: Context = context

    var mPreferencesHelper: PreferencesHelper = preferencesHelper

    override fun getString(key: String): String {
        TODO("Not yet implemented")
    }

    override fun setString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun getCountryCode(): Observable<String?>? {
        TODO("Not yet implemented")
    }

    override fun getCommonResponse(url: String?): Observable<String?>? {
        TODO("Not yet implemented")
    }
}