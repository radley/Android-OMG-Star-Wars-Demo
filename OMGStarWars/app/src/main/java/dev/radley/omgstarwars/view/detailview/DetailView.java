package dev.radley.omgstarwars.view.detailview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Base class for Detail text layouts
 */
public class DetailView extends LinearLayout {

    Context context;

    public DetailView(Context context) {
        super(context);

        this.context = context;
    }

    /**
     * Function shortcut for applying text to textView
     * makes it more readable
     *
     * @param id   int
     * @param text String
     */
    void setText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }
}
