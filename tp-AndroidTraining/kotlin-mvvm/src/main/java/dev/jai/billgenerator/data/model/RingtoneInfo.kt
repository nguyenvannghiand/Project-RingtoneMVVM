package dev.jai.billgenerator.data.model

import android.text.TextUtils
import java.io.Serializable
import java.lang.NumberFormatException
import java.util.*

data class RingtoneInfo(
        var id: String,
        var name: String,
        var isSettingShowing: Boolean,
        var rings: List<Ringtone>
) : Serializable {
    companion object {
        const val MAX_ORDER = 20000
        const val MIN_ORDER = 10000
    }

    //
    private var imgUrl: String = ""
    private var order: String = ""
    private var oderIndex: Int = 0

    //
    public fun getImgUrl(): String {
        return imgUrl
    }
    public fun setImgUrl(imgUrl: String) {
        this.imgUrl = imgUrl
    }

    public fun getOrder(): Int {
        return this.oderIndex
    }

    public fun generateOrderIndex() {
        if (TextUtils.isEmpty(order)) {
            this.oderIndex = getRandomOrder()
        }
        try {
            this.oderIndex = order.toInt()

        } catch (e: NumberFormatException) {
            this.oderIndex = getRandomOrder()
        }
    }
    private fun getRandomOrder(): Int {
        var random: Random = Random()
        return random.nextInt(MAX_ORDER - MIN_ORDER) + MIN_ORDER
    }

}