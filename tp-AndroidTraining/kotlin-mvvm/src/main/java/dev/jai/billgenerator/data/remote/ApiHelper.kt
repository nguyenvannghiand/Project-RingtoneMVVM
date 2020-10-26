package dev.jai.billgenerator.data.remote

import io.reactivex.Observable

interface ApiHelper {
    fun getCountryCode(): Observable<String?>?

    fun getCommonResponse(url: String?): Observable<String?>?
}