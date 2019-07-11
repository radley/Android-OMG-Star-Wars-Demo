package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * Species model represents a type of person or character within the Star Wars Universe.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001e\u0010\u0013\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001e\u0010\u0016\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR\u001e\u0010\u0019\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR\u001a\u0010\u001c\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR&\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R&\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010 8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\"\"\u0004\b\'\u0010$R\u001e\u0010(\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0007\"\u0004\b*\u0010\t\u00a8\u0006+"}, d2 = {"Ldev/radley/omgstarwars/models/Species;", "Ldev/radley/omgstarwars/models/SWModel;", "Ljava/io/Serializable;", "()V", "averageHeight", "", "getAverageHeight", "()Ljava/lang/String;", "setAverageHeight", "(Ljava/lang/String;)V", "averageLifespan", "getAverageLifespan", "setAverageLifespan", "classification", "getClassification", "setClassification", "designation", "getDesignation", "setDesignation", "eyeColors", "getEyeColors", "setEyeColors", "hairColors", "getHairColors", "setHairColors", "homeWorld", "getHomeWorld", "setHomeWorld", "language", "getLanguage", "setLanguage", "peopleUrls", "Ljava/util/ArrayList;", "getPeopleUrls", "()Ljava/util/ArrayList;", "setPeopleUrls", "(Ljava/util/ArrayList;)V", "relatedFilms", "getRelatedFilms", "setRelatedFilms", "skinColors", "getSkinColors", "setSkinColors", "app_debug"})
public final class Species extends dev.radley.omgstarwars.models.SWModel implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String classification;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String designation;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String language;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "average_height")
    private java.lang.String averageHeight;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "average_lifespan")
    private java.lang.String averageLifespan;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "eye_colors")
    private java.lang.String eyeColors;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "hair_colors")
    private java.lang.String hairColors;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "skin_colors")
    private java.lang.String skinColors;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "homeworld")
    private java.lang.String homeWorld;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "people")
    private java.util.ArrayList<java.lang.String> peopleUrls;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "films")
    private java.util.ArrayList<java.lang.String> relatedFilms;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClassification() {
        return null;
    }
    
    public final void setClassification(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDesignation() {
        return null;
    }
    
    public final void setDesignation(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguage() {
        return null;
    }
    
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAverageHeight() {
        return null;
    }
    
    public final void setAverageHeight(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAverageLifespan() {
        return null;
    }
    
    public final void setAverageLifespan(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEyeColors() {
        return null;
    }
    
    public final void setEyeColors(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHairColors() {
        return null;
    }
    
    public final void setHairColors(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSkinColors() {
        return null;
    }
    
    public final void setSkinColors(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHomeWorld() {
        return null;
    }
    
    public final void setHomeWorld(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.ArrayList<java.lang.String> getPeopleUrls() {
        return null;
    }
    
    public final void setPeopleUrls(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedFilms() {
        return null;
    }
    
    public void setRelatedFilms(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    public Species() {
        super();
    }
}