package dev.radley.omgstarwars.widget


import android.content.Context
import android.util.AttributeSet
import android.view.InflateException
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView with support for grid animations.
 *
 * Based on:
 * https://gist.github.com/Musenkishi/8df1ab549857756098ba
 * Credit to Freddie (Musenkishi) Lust-Hed
 *
 * ...which in turn is based on the GridView implementation of attachLayoutParameters(...):
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/GridView.java
 *
 */
class GridRecyclerView : RecyclerView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun attachLayoutAnimationParameters(child: View, params: ViewGroup.LayoutParams,
                                                 index: Int, count: Int) {

        val layoutManager = layoutManager

        if (adapter != null && layoutManager is GridLayoutManager) {

            lateinit var animationParams: GridLayoutAnimationController.AnimationParameters

            try {
                animationParams = params.layoutAnimationParameters as GridLayoutAnimationController.AnimationParameters

            } catch (e: TypeCastException) {

                animationParams = GridLayoutAnimationController.AnimationParameters()
                params.layoutAnimationParameters = animationParams
            }

            // Next we are updating the parameters

            // Set the number of items in the RecyclerView and the index of this item
            animationParams.count = count
            animationParams.index = index

            // Calculate the number of columns and rows in the grid
            val columns = layoutManager.spanCount
            animationParams.columnsCount = columns
            animationParams.rowsCount = count / columns

            // Calculate the column/row position in the grid
            val invertedIndex = count - 1 - index
            animationParams.column = columns - 1 - invertedIndex % columns
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns

        } else {
            // Proceed as normal if using another type of LayoutManager
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }
}
