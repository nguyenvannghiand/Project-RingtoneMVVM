package dev.jai.billgenerator.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import dev.jai.billgenerator.di.PreferenceInfo
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String?
) : PreferencesHelper {
    private var mPrefs: SharedPreferences? = null

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }
    override fun getString(key: String): String {
        TODO("Not yet implemented")
    }

    override fun setString(key: String, value: String) {
        TODO("Not yet implemented")
    }

}