/*
 * Copyright (C) 2018 Radley Marx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.radley.omgstarwars.listeners;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public abstract class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private final GestureDetector gestureDetector;

    /**
     * Detect when item in recyclerView is touched (onSingleTapUp) and return position
     *
     * @param context Context
     */
    public RecyclerTouchListener(Context context) {

        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });
    }

    /**
     *
     * @param holder ViewHolder
     * @param position ViewHolder position
     */
    public abstract void onItemSelected(RecyclerView.ViewHolder holder, int position);

    @Override
    public final boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (gestureDetector.onTouchEvent(e)) {
            View touchedView = rv.findChildViewUnder(e.getX(), e.getY());
            
            if(touchedView != null)
                onItemSelected(rv.findContainingViewHolder(touchedView),
                        rv.getChildAdapterPosition(touchedView));
        }
        return false;
    }

    /**
     * Ignore unused touch events
     *
     * @param rv RecyclerView
     * @param e MotionEvent
     */
    @Override
    public final void onTouchEvent(RecyclerView rv, MotionEvent e) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Ignore unused child / ancestor exclusions
     *
     * @param disallowIntercept boolean
     */
    @Override
    public final void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
