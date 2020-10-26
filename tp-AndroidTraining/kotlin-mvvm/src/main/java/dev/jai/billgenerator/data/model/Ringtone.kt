package dev.jai.billgenerator.data.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

data class Ringtone(
        var id: String = "",
        var name: String = "",
        var ringUrl: String = "",
        var file: String = "",
        var code: Int = 0,
        var index: Int = 0,
        var loadingTime: Long = -1,
        var duration: Int = 0,
        var currentPosition: Int = -1,
        var rings: List<Ringtone>

) : Serializable {
    //
    companion object {
        const val TRUE: Int = 1
        const val FALSE: Int = 0

        @JvmStatic
        fun newRingtone(id: String, name: String, path: String): Ringtone {
            val ringtone: Ringtone = Ringtone(name, path).id(id).online(TRUE)
            return ringtone
        }

        @JvmStatic
        fun getType(): Type {
            return object : TypeToken<Ringtone>() {}.type
        }

        @JvmStatic
        fun getTypeList(): Type {
            return object : TypeToken<List<Ringtone>>() {}.type
        }
    }

    private var isOnline: Int = TRUE
    private var isFavorite: Int = FALSE

    //
    private var count: String = "0"
    private var isRequestedRing: Boolean = false
    private var createdDate: Long = 0
    private var isLoading: Boolean = false

    //
    constructor()
    constructor(name: String, path: String) {
        this.name = name
        this.ringUrl = path
        buildCode()
    }

    //
    public fun id(id: String): Ringtone {
        this.id = id
        return this
    }

    public fun file(file: String): Ringtone {
        this.file = file
        return this
    }

    public fun isRingtone(): Boolean {
        return ringUrl != null && !ringUrl.isEmpty() || file != null && !file.isEmpty()
    }

    public fun isOnline(): Boolean {
        return this.isOnline == TRUE
    }

    public fun setOnline(online: Boolean) {
        this.isOnline = if (online == true) TRUE else FALSE
    }

    public fun setOnline(online: Int) {
        this.isOnline = online
    }

    public fun online(online: Int): Ringtone {
        this.isOnline = online
        return this
    }

    public fun isFavorite(): Boolean {
        return isFavorite == TRUE
    }

    public fun setFavorite(favorite: Boolean) {
        isFavorite = if (favorite == true) TRUE else FALSE
    }

    public fun setFavorite(favorite: Int) {
        this.isFavorite = favorite
    }

    public fun setIndex(index: Int): Ringtone {
        this.index = index
        return this
    }

    public fun getCount(): String {
        return count
    }

    public fun setCount(count: String): Ringtone {
        this.count = count
        return this
    }

    override fun toString(): String {
        return Gson().toJson(this, getType())
    }

    public fun buildCode(): Int {
        if (code == 0 && ringUrl != null) {
            this.code = ringUrl.hashCode()
        }
        return code
    }

    override fun equals(other: Any?): Boolean {
        if (super.equals(other)) {
            return true
        }
        if (other is Ringtone) {
            if (buildCode() > 0) {
                return (code == (other as Ringtone).code)
            }
            return (ringUrl != null) && ringUrl.equals((other as Ringtone).ringUrl)
        }
        return false
    }

    public fun isRequestedRing(): Boolean {
        return isRequestedRing
    }

    public fun setRequestedRing(requestedRing: Boolean): Ringtone {
        isRequestedRing = requestedRing
        return this
    }

    public fun getCreatedDate(): Long {
        if (createdDate > 0) {
            return createdDate
        } else {
            return System.currentTimeMillis()
        }
    }

    public fun setLoading(loading: Boolean): Ringtone {
        if (loading) {
            loadingTime = System.currentTimeMillis()
        } else if (this.loadingTime > 0) {
            loadingTime = System.currentTimeMillis() - loadingTime
        }
        isLoading = loading
        return this
    }
    public fun isLoading(): Boolean {
        return isLoading
    }

}