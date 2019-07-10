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

package dev.radley.omgstarwars.bundle;

import android.content.Context;
import android.content.Intent;

import dev.radley.omgstarwars.view.DetailActivity;
import dev.radley.omgstarwars.models.SWModel;

/**
 * Holding intent extra names and utility methods for intent handling.
 */
public class DetailExtras {
    public static final String MODEL = "MODEL";

    private static Intent intent;

    /**
     * Intent builder for Detail Activity
     *
     * @param context
     * @param item SWModel
     * @return intent
     */
    public static Intent getIntent(Context context, SWModel item) {

        intent = new Intent(context, DetailActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(DetailExtras.MODEL, item);

        return intent;
    }

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
