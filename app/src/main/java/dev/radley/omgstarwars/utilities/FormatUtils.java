package dev.radley.omgstarwars.utilities;

import android.annotation.SuppressLint;
import android.content.Context;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import dev.radley.omgstarwars.R;

/**
 * uick utility functions
 */
public class FormatUtils {


    /**
     * Extracts item id from swapi url
     *
     * @param url String
     * @return id of item
     */
    public static int getId(String url) {

        // example: https://swapi.co/api/films/2/ -> returns "2"

        String[] string = url.split("/");
        return Integer.parseInt(string[string.length-1]);
    }

    /**
     * - trims outer white space in query text (i.e. "star " or " star")
     * - keeps inner white spaces (i.e. "star d")
     * - removes all other non-alphanumeric characters
     * - converts to lowercase
     *
     * @param query String
     * @return trimmed query String
     */
    public static String getTrimmedQuery(String query) {

        // only remove outer whitespace
        query = query.trim();
        query = query.replaceAll("[^a-zA-Z0-9\\s]", "");
        return query.toLowerCase();
    }


    /**
     * Date formatting
     *
     * @param date String
     * @return formatted <code>date</code>
     */
    public static String getFormattedDate(Context context, String date) {

        return Instant.parse(date)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern(context.getResources().getString(R.string.detail_date_format)));
    }

    /**
     * Tries to add commas to long numbers
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    @SuppressLint("DefaultLocale")
    public static String getFormattedNumber(String value) {

        try {
            long number = Long.parseLong(value);
            return String.format("%,d", number);
        } catch (NumberFormatException error) {
            return value;
        }
    }

    /**
     * Returns height w/ "cm" if height has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedHeightCm(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_height_cm, value);
    }

    /**
     * Returns weight w/ "kg" if weight has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedKg(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_mass_kg, getFormattedNumber(value));
    }

    /**
     * Returns duration w/ "days" if duration has a value
     * - used for orbital period for planets
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedDays(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_period_days, getFormattedNumber(value));
    }

    /**
     * Returns duration w/ "years" if duration has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedYears(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_duraction_years, getFormattedNumber(value));
    }

    /**
     * Returns distance w/ "km" if distance has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedDistance(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_distance_km, getFormattedNumber(value));
    }

    /**
     * Returns percentage w/ "%" if percentage has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedPercentage(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_percent, value);
    }

    /**
     * Returns length w/ "m" if length has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedLengthM(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_length_m, getFormattedNumber(value));
    }

    /**
     * Returns tonnage w/ "metric tons" if tonnage has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedTonnage(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_tonnage, getFormattedNumber(value));
    }

    /**
     * Returns credits w/ "CR" if credits has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedCredits(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return (context.getString(R.string.detail_credits, getFormattedNumber(value)));
    }

    /**
     * Returns speed w/ "kph" if speed has a value
     *
     * @param value String
     * @return formatted <code>value</code>
     */
    public static String getFormattedSpeedKph(Context context, String value) {

        if(isUnknown(context, value))
            return value;

        return context.getString(R.string.detail_speed_kph, getFormattedNumber(value));
    }

    /**
     * Checks to see if value has known value
     * (so value can be formatted)
     *
     * @param value String
     * @return boolean
     */
    public static boolean isUnknown(Context context, String value) {
        return value.equals("") ||
                value.toLowerCase().equals(context.getString(R.string.detail_na)) ||
                value.toLowerCase().equals(context.getString(R.string.unknown));

    }
}
