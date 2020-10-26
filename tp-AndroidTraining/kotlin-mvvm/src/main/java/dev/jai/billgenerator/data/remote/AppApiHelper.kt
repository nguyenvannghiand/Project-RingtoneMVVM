package dev.jai.billgenerator.data.remote

import io.reactivex.Observable
import javax.inject.Inject

class AppApiHelper @Inject constructor() : ApiHelper {

    override fun getCountryCode(): Observable<String?>? {
        TODO("Not yet implemented")
    }

    override fun getCommonResponse(url: String?): Observable<String?>? {
        TODO("Not yet implemented")
    }
}