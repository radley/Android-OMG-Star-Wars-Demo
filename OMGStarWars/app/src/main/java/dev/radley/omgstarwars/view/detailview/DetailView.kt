package dev.radley.omgstarwars.view.detailview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Base class for Detail text layouts
 */
open class DetailView(context: Context) : LinearLayout(context) {

    /**
     * Function shortcut for applying text to textView
     * makes it more readable
     *
     * @param id   int
     * @param text String
     */
    public fun setText(id: Int, text: String) {
        findViewById<TextView>(id).text = text
    }
}
