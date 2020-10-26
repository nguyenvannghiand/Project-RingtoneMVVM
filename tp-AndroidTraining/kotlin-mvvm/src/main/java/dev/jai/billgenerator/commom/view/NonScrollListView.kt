package dev.jai.billgenerator.commom.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ListView

class NonScrollListView : ListView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    @SuppressLint("Range")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Int.MAX_VALUE, MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom)
        var params: ViewGroup.LayoutParams = layoutParams
        params.height = measuredHeight
    }
}