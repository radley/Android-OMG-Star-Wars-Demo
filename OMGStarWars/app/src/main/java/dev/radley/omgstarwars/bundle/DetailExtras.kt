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

package dev.radley.omgstarwars.bundle

import android.content.Context
import android.content.Intent

import dev.radley.omgstarwars.view.DetailActivity
import dev.radley.omgstarwars.models.SWModel

/**
 * Holding intent extra names and utility methods for intent handling.
 */
object DetailExtras {

    const val MODEL = "MODEL"

    /**
     * Intent builder for Detail Activity
     *
     * @param context
     * @param item SWModel
     * @return intent
     */
    fun getIntent(context: Context, item: SWModel): Intent {

        val intent = Intent(context, DetailActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        intent.putExtra(MODEL, item)

        return intent
    }


    // TODO: implement SearchExtra.hasAll()

    /**
     * Checks if all extras are present in an intent.
     *
     * @param intent The intent to check.
     * @param extras The extras to check for.
     * @return `true` if all extras are present, else `false`.
     */
    fun hasAll(intent: Intent, vararg extras: String): Boolean {
        for (extra in extras) {
            if (!intent.hasExtra(extra)) {
                return false
            }
        }
        return true
    }
}
