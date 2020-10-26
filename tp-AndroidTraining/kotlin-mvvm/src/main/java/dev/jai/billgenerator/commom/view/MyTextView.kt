package dev.jai.billgenerator.commom.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import dev.jai.billgenerator.utils.AppUtils

class MyTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        init()
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(context, attributes, defStyleAttr) {
        init()
    }

    private fun init() {
        this.typeface = AppUtils.getTypeface(context)
    }
}