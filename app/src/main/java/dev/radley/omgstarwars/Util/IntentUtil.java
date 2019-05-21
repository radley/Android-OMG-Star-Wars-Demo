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

package dev.radley.omgstarwars.Util;

import android.content.Intent;

/**
 * Holding intent extra names and utility methods for intent handling.
 */
public class IntentUtil {
    public static final String PHOTO = "PHOTO";
    public static final String PHOTO_LIST = "PHOTO_LIST";
    public static final String SELECTED_ITEM_POSITION = "SELECTED_ITEM_POSITION";
    public static final String SHARED_ELEMENT_TRANSITION = "SHARED_ELEMENT_TRANSITION";
    public static final String DETAIL_ENTER_TRANSITION = "DETAIL_ENTER_TRANSITION";
    public static final String DETAIL_EXIT_TRANSITION = "DETAIL_EXIT_TRANSITION";
    public static final int REQUEST_CODE = 0;

    /**
     * Checks if all extras are present in an intent.
     *
     * @param intent The intent to check.
     * @param extras The extras to check for.
     * @return <code>true</code> if all extras are present, else <code>false</code>.
     */
    public static boolean hasAll(Intent intent, String... extras) {
        for (String extra : extras) {
            if (!intent.hasExtra(extra)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any extra is present in an intent.
     *
     * @param intent The intent to check.
     * @param extras The extras to check for.
     * @return <code>true</code> if any checked extra is present, else <code>false</code>.
     */
    public static boolean hasAny(Intent intent, String... extras) {
        for (String extra : extras) {
            if (intent.hasExtra(extra)) {
                return true;
            }
        }
        return false;
    }
}
