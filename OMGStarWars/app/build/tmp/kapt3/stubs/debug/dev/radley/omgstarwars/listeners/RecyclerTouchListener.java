package dev.radley.omgstarwars.listeners;

import java.lang.System;

/**
 * Detect when item in recyclerView is touched (onSingleTapUp) and return position
 *
 * @param context Context
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Ldev/radley/omgstarwars/listeners/RecyclerTouchListener;", "Landroidx/recyclerview/widget/RecyclerView$OnItemTouchListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "gestureDetector", "Landroid/view/GestureDetector;", "onInterceptTouchEvent", "", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "e", "Landroid/view/MotionEvent;", "onItemSelected", "", "holder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "position", "", "onRequestDisallowInterceptTouchEvent", "disallowIntercept", "onTouchEvent", "app_debug"})
public abstract class RecyclerTouchListener implements androidx.recyclerview.widget.RecyclerView.OnItemTouchListener {
    private final android.view.GestureDetector gestureDetector = null;
    
    /**
     * @param holder ViewHolder
     * @param position ViewHolder position
     */
    public abstract void onItemSelected(@org.jetbrains.annotations.Nullable()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position);
    
    @java.lang.Override()
    public boolean onInterceptTouchEvent(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView rv, @org.jetbrains.annotations.NotNull()
    android.view.MotionEvent e) {
        return false;
    }
    
    /**
     * Ignore unused touch events
     *
     * @param rv RecyclerView
     * @param e MotionEvent
     */
    @java.lang.Override()
    public void onTouchEvent(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView rv, @org.jetbrains.annotations.NotNull()
    android.view.MotionEvent e) {
    }
    
    /**
     * Ignore unused child / ancestor exclusions
     *
     * @param disallowIntercept boolean
     */
    @java.lang.Override()
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
    
    public RecyclerTouchListener(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super();
    }
}