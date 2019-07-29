package dev.radley.omgstarwars.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.text.CustomTypefaceSpan
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


/**
 * uick utility functions
 */
object FormatUtils {


    /**
     * Extracts item id from swapi url
     *
     * @param url String
     * @return id of item
     */
    fun getId(url: String): Int {

        // example: https://swapi.co/api/films/2/ -> returns "2"

        val string = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(string[string.size - 1])
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
    fun getTrimmedQuery(query: String): String {

        // only remove outer whitespace
        var newQuery = query.trim { it <= ' ' }
        newQuery = newQuery.replace("[^a-zA-Z0-9\\s]".toRegex(), "")
        return newQuery.toLowerCase()
    }


    /**
     * Date formatting
     *
     * @param date String
     * @return formatted `date`
     */
    fun getFormattedDate(context: Context, date: String): String {

        return Instant.parse(date)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern(context.resources.getString(R.string.detail_date_format)))
    }

    /**
     * Tries to add commas to long numbers
     *
     * @param value String
     * @return formatted `value`
     */
    @SuppressLint("DefaultLocale")
    fun getFormattedNumber(value: String): String {

        return try {
            val number = java.lang.Long.parseLong(value)
            String.format("%,d", number)
        } catch (error: NumberFormatException) {
            value
        }

    }

    /**
     * Returns height w/ "cm" if height has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedHeightCm(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_height_cm, value)

    }

    /**
     * Returns weight w/ "kg" if weight has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedKg(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_mass_kg, getFormattedNumber(value))

    }

    /**
     * Returns duration w/ "days" if duration has a value
     * - used for orbital period for planets
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedDays(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_period_days, getFormattedNumber(value))

    }

    /**
     * Returns duration w/ "years" if duration has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedYears(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_duraction_years, getFormattedNumber(value))

    }

    /**
     * Returns distance w/ "km" if distance has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedDistance(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_distance_km, getFormattedNumber(value))

    }

    /**
     * Returns percentage w/ "%" if percentage has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedPercentage(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_percent, value)

    }

    /**
     * Returns length w/ "m" if length has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedLengthM(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_length_m, getFormattedNumber(value))

    }

    /**
     * Returns tonnage w/ "metric tons" if tonnage has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedTonnage(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_tonnage, getFormattedNumber(value))

    }

    /**
     * Returns credits w/ "CR" if credits has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedCredits(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_credits, getFormattedNumber(value))

    }

    /**
     * Returns speed w/ "kph" if speed has a value
     *
     * @param value String
     * @return formatted `value`
     */
    fun getFormattedSpeedKph(context: Context, value: String): String {

        return if (isUnknown(context, value)) value else context.getString(R.string.detail_speed_kph, getFormattedNumber(value))

    }

    fun getEmphasizedText(text: String, boldText: String): SpannableString {

        val boldTypeface = Typeface.create("sans-serif-black", Typeface.NORMAL)

        val spannable = SpannableString(text)

        if (text.toLowerCase().contains(boldText.toLowerCase())) {

            spannable.setSpan(CustomTypefaceSpan("", boldTypeface),
                    text.toLowerCase().indexOf(boldText.toLowerCase()),
                    text.toLowerCase().indexOf(boldText.toLowerCase()) + boldText.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }

        return spannable
    }

    fun IntegerToRomanNumeral(value: Int): String {
        var input = value

        if (input < 1 || input > 3999) // invalid number
            return value.toString()

        var s = ""
        while (input >= 1000) {
            s += "M"
            input -= 1000
        }
        while (input >= 900) {
            s += "CM"
            input -= 900
        }
        while (input >= 500) {
            s += "D"
            input -= 500
        }
        while (input >= 400) {
            s += "CD"
            input -= 400
        }
        while (input >= 100) {
            s += "C"
            input -= 100
        }
        while (input >= 90) {
            s += "XC"
            input -= 90
        }
        while (input >= 50) {
            s += "L"
            input -= 50
        }
        while (input >= 40) {
            s += "XL"
            input -= 40
        }
        while (input >= 10) {
            s += "X"
            input -= 10
        }
        while (input >= 9) {
            s += "IX"
            input -= 9
        }
        while (input >= 5) {
            s += "V"
            input -= 5
        }
        while (input >= 4) {
            s += "IV"
            input -= 4
        }
        while (input >= 1) {
            s += "I"
            input -= 1
        }
        return s
    }


    /**
     * Checks to see if value has known value
     * (so value can be formatted)
     *
     * @param value String
     * @return boolean
     */
    private fun isUnknown(context: Context, value: String): Boolean {
        return value == "" ||
                value.toLowerCase() == context.getString(R.string.detail_na) ||
                value.toLowerCase() == context.getString(R.string.unknown)
    }
}
