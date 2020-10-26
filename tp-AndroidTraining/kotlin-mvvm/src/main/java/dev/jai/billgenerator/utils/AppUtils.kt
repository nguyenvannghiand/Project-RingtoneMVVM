package dev.jai.billgenerator.utils

import android.content.Context
import android.graphics.Typeface
import java.math.RoundingMode
import java.text.DecimalFormat

class AppUtils {
    companion object {
        private const val salesTax = 10.0F;
        private const val importDuty = 5.0F;

        fun getTypeface(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/svn_avo.ttf")
        }

        fun getTypefaceBold(context: Context): Typeface {
            return Typeface.createFromAsset(context.assets, "fonts/svn_avo_bold.ttf")
        }


        fun getImportDuty(price: Float): Float {
            return (price * importDuty / 100)
        }

        fun getSalesTax(price: Float): Float {
            return (price * salesTax / 100)
        }

        fun formatDecimal(value: Float): String {
            val df = DecimalFormat("#,##,###.00")
            df.roundingMode = RoundingMode.CEILING
            return df.format(Math.round(value * 20.0) / 20.0)
        }
    }
}