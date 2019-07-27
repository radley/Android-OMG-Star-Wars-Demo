package dev.radley.omgstarwars.utilities;

import java.lang.System;

/**
 * uick utility functions
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u000f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0010\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0016\u0010\u0012\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0013\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0014\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0004J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a8\u0006\u001c"}, d2 = {"Ldev/radley/omgstarwars/utilities/FormatUtils;", "", "()V", "IntegerToRomanNumeral", "", "value", "", "getFormattedCredits", "context", "Landroid/content/Context;", "getFormattedDate", "date", "getFormattedDays", "getFormattedDistance", "getFormattedHeightCm", "getFormattedKg", "getFormattedLengthM", "getFormattedNumber", "getFormattedPercentage", "getFormattedSpeedKph", "getFormattedTonnage", "getFormattedYears", "getId", "url", "getTrimmedQuery", "query", "isUnknown", "", "app_debug"})
public final class FormatUtils {
    public static final dev.radley.omgstarwars.utilities.FormatUtils INSTANCE = null;
    
    /**
     * Extracts item id from swapi url
     *
     * @param url String
     * @return id of item
     */
    public final int getId(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
        return 0;
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
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTrimmedQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    /**
     * Date formatting
     *
     * @param date String
     * @return formatted `date`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDate(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String date) {
        return null;
    }
    
    /**
     * Tries to add commas to long numbers
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    @android.annotation.SuppressLint(value = {"DefaultLocale"})
    public final java.lang.String getFormattedNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns height w/ "cm" if height has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedHeightCm(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns weight w/ "kg" if weight has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedKg(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns duration w/ "days" if duration has a value
     * - used for orbital period for planets
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDays(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns duration w/ "years" if duration has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedYears(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns distance w/ "km" if distance has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDistance(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns percentage w/ "%" if percentage has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedPercentage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns length w/ "m" if length has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedLengthM(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns tonnage w/ "metric tons" if tonnage has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedTonnage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns credits w/ "CR" if credits has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedCredits(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Returns speed w/ "kph" if speed has a value
     *
     * @param value String
     * @return formatted `value`
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedSpeedKph(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    /**
     * Checks to see if value has known value
     * (so value can be formatted)
     *
     * @param value String
     * @return boolean
     */
    private final boolean isUnknown(android.content.Context context, java.lang.String value) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String IntegerToRomanNumeral(int value) {
        return null;
    }
    
    private FormatUtils() {
        super();
    }
}