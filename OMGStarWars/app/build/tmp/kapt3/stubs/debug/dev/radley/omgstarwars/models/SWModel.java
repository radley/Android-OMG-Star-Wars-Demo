package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * Base model for swapi models
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0006\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\nR\u0011\u0010\u000e\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0006R\u0011\u0010\u0010\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0006R\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\nR\u001c\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\u001b\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0006R\u001c\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0018R\u0014\u0010\u001f\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\u0006R\u001c\u0010#\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010\u0018R\u0011\u0010%\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u0006R\u001c\u0010\'\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u0018R\u0011\u0010)\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u0006R\u001c\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010\u0018R\u0011\u0010-\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b.\u0010\u0006R\u0014\u0010/\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b0\u0010\u0006R\u001a\u00101\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0006\"\u0004\b3\u0010\n\u00a8\u00064"}, d2 = {"Ldev/radley/omgstarwars/models/SWModel;", "Ljava/io/Serializable;", "()V", "categoryId", "", "getCategoryId", "()Ljava/lang/String;", "created", "getCreated", "setCreated", "(Ljava/lang/String;)V", "edited", "getEdited", "setEdited", "id", "getId", "imagePath", "getImagePath", "name", "getName", "setName", "planets", "Ljava/util/ArrayList;", "getPlanets", "()Ljava/util/ArrayList;", "relatedFilms", "getRelatedFilms", "relatedFilmsTitle", "getRelatedFilmsTitle", "relatedPeople", "getRelatedPeople", "relatedPeopleTitle", "getRelatedPeopleTitle", "relatedPlanetsTitle", "getRelatedPlanetsTitle", "relatedSpecies", "getRelatedSpecies", "relatedSpeciesTitle", "getRelatedSpeciesTitle", "relatedStarships", "getRelatedStarships", "relatedStarshipsTitle", "getRelatedStarshipsTitle", "relatedVehicles", "getRelatedVehicles", "relatedVehiclesTitle", "getRelatedVehiclesTitle", "title", "getTitle", "url", "getUrl", "setUrl", "app_debug"})
public class SWModel implements java.io.Serializable {
    
    /**
     * Models contain either name or title
     * This makes it uniform
     *
     * @return name or title
     */
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String url;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String created;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String edited;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    public final void setUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCreated() {
        return null;
    }
    
    public final void setCreated(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEdited() {
        return null;
    }
    
    public final void setEdited(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategoryId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImagePath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.util.ArrayList<java.lang.String> getRelatedFilms() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.util.ArrayList<java.lang.String> getRelatedPeople() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.util.ArrayList<java.lang.String> getPlanets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.util.ArrayList<java.lang.String> getRelatedSpecies() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.util.ArrayList<java.lang.String> getRelatedStarships() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.util.ArrayList<java.lang.String> getRelatedVehicles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedFilmsTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getRelatedPeopleTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedPlanetsTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedSpeciesTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedStarshipsTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRelatedVehiclesTitle() {
        return null;
    }
    
    public SWModel() {
        super();
    }
}