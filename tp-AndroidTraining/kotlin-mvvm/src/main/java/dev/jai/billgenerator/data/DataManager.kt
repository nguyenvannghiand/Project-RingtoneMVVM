package dev.jai.billgenerator.data

import dev.jai.billgenerator.data.local.pref.PreferencesHelper
import dev.jai.billgenerator.data.remote.ApiHelper
import javax.inject.Singleton

interface DataManager : PreferencesHelper, ApiHelper {
}