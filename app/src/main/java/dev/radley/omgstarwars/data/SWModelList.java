package dev.radley.omgstarwars.data;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Generic list model
 */
public class SWModelList<T> implements Serializable {
    public int count;
    public String next;
    public String previous;
    public ArrayList<T> results;
}