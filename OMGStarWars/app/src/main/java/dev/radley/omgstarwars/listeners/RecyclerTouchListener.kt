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

package dev.radley.omgstarwars.listeners

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent


abstract class RecyclerTouchListener
/**
 * Detect when item in recyclerView is touched (onSingleTapUp) and return position
 *
 * @param context Context
 */
(context: Context?) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }
            })

    /**
     *
     * @param holder ViewHolder
     * @param position ViewHolder position
     */
    abstract fun onItemSelected(holder: RecyclerView.ViewHolder?, position: Int)

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(e)) {
            val touchedView = rv.findChildViewUnder(e.x, e.y)

            if (touchedView != null)
                onItemSelected(rv.findContainingViewHolder(touchedView),
                        rv.getChildAdapterPosition(touchedView))
        }
        return false
    }

    /**
     * Ignore unused touch events
     *
     * @param rv RecyclerView
     * @param e MotionEvent
     */
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        throw UnsupportedOperationException("Not implemented")
    }

    /**
     * Ignore unused child / ancestor exclusions
     *
     * @param disallowIntercept boolean
     */
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        throw UnsupportedOperationException("Not implemented")
    }
}
