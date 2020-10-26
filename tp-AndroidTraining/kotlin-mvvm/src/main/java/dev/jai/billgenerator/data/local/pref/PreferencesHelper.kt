package dev.jai.billgenerator.data.local.pref

interface PreferencesHelper {
    fun getString( key : String) : String
    fun setString(key: String, value : String)
}