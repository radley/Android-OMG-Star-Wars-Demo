package dev.radley.omgstarwars.text

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

/**
 * Enable multiple fonts in a textView
 *
 * Solution found on StackOverflow:
 * https://stackoverflow.com/questions/4819049/how-can-i-use-typefacespan-or-stylespan-with-a-custom-typeface
 *
 */
class CustomTypefaceSpan(family: String, private val newType: Typeface?) : TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint)
    }

    private fun applyCustomTypeFace(paint: Paint) {

        newType?.let {
            val oldStyle: Int
            val old = paint.typeface

            oldStyle = old?.style ?: 0

            val fake = oldStyle and it.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }

            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }

            paint.typeface = it
        }
    }
}

