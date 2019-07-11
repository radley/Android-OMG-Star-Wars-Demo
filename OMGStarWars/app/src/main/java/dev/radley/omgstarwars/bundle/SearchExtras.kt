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

import dev.radley.omgstarwars.view.SearchActivity

/**
 * Holding intent extra names and utility methods for intent handling.
 */
object SearchExtras {

    const val QUERY = "QUERY"
    const val CATEGORY = "CATEGORY"

    /**
     * Intent & bundle builder for Search Activity
     *
     * @param context Context
     * @param query String
     * @param category String
     * @return
     */
    fun getIntent(context: Context, query: String, category: String): Intent {

        val intent = Intent(context, SearchActivity::class.java)
        intent.putExtra(QUERY, query)
        intent.putExtra(CATEGORY, category)

        return intent
    }

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
