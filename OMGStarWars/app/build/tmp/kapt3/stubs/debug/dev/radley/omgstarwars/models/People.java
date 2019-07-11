package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * People model represents an individual person or character within the Star Wars universe.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001e\u0010\r\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001e\u0010\u0013\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR&\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001a8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR&\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001a8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR&\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001a8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR&\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001a8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001c\"\u0004\b\'\u0010\u001eR\u001e\u0010(\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0007\"\u0004\b*\u0010\t\u00a8\u0006+"}, d2 = {"Ldev/radley/omgstarwars/models/People;", "Ldev/radley/omgstarwars/models/SWModel;", "Ljava/io/Serializable;", "()V", "birthYear", "", "getBirthYear", "()Ljava/lang/String;", "setBirthYear", "(Ljava/lang/String;)V", "gender", "getGender", "setGender", "hairColor", "getHairColor", "setHairColor", "height", "getHeight", "setHeight", "homeWorldUrl", "getHomeWorldUrl", "setHomeWorldUrl", "mass", "getMass", "setMass", "relatedFilms", "Ljava/util/ArrayList;", "getRelatedFilms", "()Ljava/util/ArrayList;", "setRelatedFilms", "(Ljava/util/ArrayList;)V", "relatedSpecies", "getRelatedSpecies", "setRelatedSpecies", "relatedStarships", "getRelatedStarships", "setRelatedStarships", "relatedVehicles", "getRelatedVehicles", "setRelatedVehicles", "skinColor", "getSkinColor", "setSkinColor", "app_debug"})
public final class People extends dev.radley.omgstarwars.models.SWModel implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String gender;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String height;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String mass;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "birth_year")
    private java.lang.String birthYear;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "hair_color")
    private java.lang.String hairColor;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "homeworld")
    private java.lang.String homeWorldUrl;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "skin_color")
    private java.lang.String skinColor;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "films")
    private java.util.ArrayList<java.lang.String> relatedFilms;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "species")
    private java.util.ArrayList<java.lang.String> relatedSpecies;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "starships")
    private java.util.ArrayList<java.lang.String> relatedStarships;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "vehicles")
    private java.util.ArrayList<java.lang.String> relatedVehicles;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGender() {
        return null;
    }
    
    public final void setGender(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHeight() {
        return null;
    }
    
    public final void setHeight(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMass() {
        return null;
    }
    
    public final void setMass(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBirthYear() {
        return null;
    }
    
    public final void setBirthYear(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHairColor() {
        return null;
    }
    
    public final void setHairColor(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHomeWorldUrl() {
        return null;
    }
    
    public final void setHomeWorldUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSkinColor() {
        return null;
    }
    
    public final void setSkinColor(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedFilms() {
        return null;
    }
    
    public void setRelatedFilms(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedSpecies() {
        return null;
    }
    
    public void setRelatedSpecies(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedStarships() {
        return null;
    }
    
    public void setRelatedStarships(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedVehicles() {
        return null;
    }
    
    public void setRelatedVehicles(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    public People() {
        super();
    }
}