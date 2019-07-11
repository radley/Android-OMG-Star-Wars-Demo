package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * Starship model represents a single transport craft that has hyperdrive capability.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001e\u0010\r\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\t\u00a8\u0006\u0010"}, d2 = {"Ldev/radley/omgstarwars/models/Starship;", "Ldev/radley/omgstarwars/models/Vehicle;", "Ljava/io/Serializable;", "()V", "hyperdriveRating", "", "getHyperdriveRating", "()Ljava/lang/String;", "setHyperdriveRating", "(Ljava/lang/String;)V", "mglt", "getMglt", "setMglt", "starshipClass", "getStarshipClass", "setStarshipClass", "app_debug"})
public final class Starship extends dev.radley.omgstarwars.models.Vehicle implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "starship_class")
    private java.lang.String starshipClass;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "hyperdrive_rating")
    private java.lang.String hyperdriveRating;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "MGLT")
    private java.lang.String mglt;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStarshipClass() {
        return null;
    }
    
    public final void setStarshipClass(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHyperdriveRating() {
        return null;
    }
    
    public final void setHyperdriveRating(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMglt() {
        return null;
    }
    
    public final void setMglt(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public Starship() {
        super();
    }
}