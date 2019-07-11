package dev.radley.omgstarwars.text;

import java.lang.System;

/**
 * Enable multiple fonts in a textView
 *
 * Solution found on StackOverflow:
 * https://stackoverflow.com/questions/4819049/how-can-i-use-typefacespan-or-stylespan-with-a-custom-typeface
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\rH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Ldev/radley/omgstarwars/text/CustomTypefaceSpan;", "Landroid/text/style/TypefaceSpan;", "family", "", "newType", "Landroid/graphics/Typeface;", "(Ljava/lang/String;Landroid/graphics/Typeface;)V", "applyCustomTypeFace", "", "paint", "Landroid/graphics/Paint;", "updateDrawState", "ds", "Landroid/text/TextPaint;", "updateMeasureState", "app_debug"})
public final class CustomTypefaceSpan extends android.text.style.TypefaceSpan {
    private final android.graphics.Typeface newType = null;
    
    @java.lang.Override()
    public void updateDrawState(@org.jetbrains.annotations.NotNull()
    android.text.TextPaint ds) {
    }
    
    @java.lang.Override()
    public void updateMeasureState(@org.jetbrains.annotations.NotNull()
    android.text.TextPaint paint) {
    }
    
    private final void applyCustomTypeFace(android.graphics.Paint paint) {
    }
    
    public CustomTypefaceSpan(@org.jetbrains.annotations.NotNull()
    java.lang.String family, @org.jetbrains.annotations.Nullable()
    android.graphics.Typeface newType) {
        super(null);
    }
}