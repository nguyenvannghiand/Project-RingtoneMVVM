package dev.jai.billgenerator.commom.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import dev.jai.billgenerator.utils.AppUtils

class MyTextViewBold : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init() {
        this.typeface = AppUtils.getTypefaceBold(context)
    }
}