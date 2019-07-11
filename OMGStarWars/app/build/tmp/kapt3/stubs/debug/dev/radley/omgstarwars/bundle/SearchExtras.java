package dev.radley.omgstarwars.bundle;

import java.lang.System;

/**
 * Holding intent extra names and utility methods for intent handling.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\'\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0010\"\u00020\u0004\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Ldev/radley/omgstarwars/bundle/SearchExtras;", "", "()V", "CATEGORY", "", "QUERY", "getIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "query", "category", "hasAll", "", "intent", "extras", "", "(Landroid/content/Intent;[Ljava/lang/String;)Z", "app_debug"})
public final class SearchExtras {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String QUERY = "QUERY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CATEGORY = "CATEGORY";
    public static final dev.radley.omgstarwars.bundle.SearchExtras INSTANCE = null;
    
    /**
     * Intent & bundle builder for Search Activity
     *
     * @param context Context
     * @param query String
     * @param category String
     * @return
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent getIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    /**
     * Checks if all extras are present in an intent.
     *
     * @param intent The intent to check.
     * @param extras The extras to check for.
     * @return `true` if all extras are present, else `false`.
     */
    public final boolean hasAll(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, @org.jetbrains.annotations.NotNull()
    java.lang.String... extras) {
        return false;
    }
    
    private SearchExtras() {
        super();
    }
}