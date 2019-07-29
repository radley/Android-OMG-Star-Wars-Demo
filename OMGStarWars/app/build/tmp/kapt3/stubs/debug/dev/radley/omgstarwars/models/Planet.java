package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * Planet model represents a large mass, planet or planetoid in the Star Wars Universe, at the time of 0 ABY.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001e\u0010\u0010\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR&\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00178\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR&\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00178\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001bR\u0014\u0010\u001f\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0007R\u001e\u0010!\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0007\"\u0004\b#\u0010\tR\u0014\u0010$\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u0007R\u001e\u0010&\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0007\"\u0004\b(\u0010\tR\u001a\u0010)\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0007\"\u0004\b+\u0010\t\u00a8\u0006,"}, d2 = {"Ldev/radley/omgstarwars/models/Planet;", "Ldev/radley/omgstarwars/models/SWModel;", "Ljava/io/Serializable;", "()V", "climate", "", "getClimate", "()Ljava/lang/String;", "setClimate", "(Ljava/lang/String;)V", "diameter", "getDiameter", "setDiameter", "gravity", "getGravity", "setGravity", "orbitalPeriod", "getOrbitalPeriod", "setOrbitalPeriod", "population", "getPopulation", "setPopulation", "relatedFilms", "Ljava/util/ArrayList;", "getRelatedFilms", "()Ljava/util/ArrayList;", "setRelatedFilms", "(Ljava/util/ArrayList;)V", "relatedPeople", "getRelatedPeople", "setRelatedPeople", "relatedPeopleTitle", "getRelatedPeopleTitle", "rotationPeriod", "getRotationPeriod", "setRotationPeriod", "subtitle", "getSubtitle", "surfaceWater", "getSurfaceWater", "setSurfaceWater", "terrain", "getTerrain", "setTerrain", "app_debug"})
public final class Planet extends dev.radley.omgstarwars.models.SWModel implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String diameter;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String gravity;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String population;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String climate;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String terrain;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "rotation_period")
    private java.lang.String rotationPeriod;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "orbital_period")
    private java.lang.String orbitalPeriod;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "surface_water")
    private java.lang.String surfaceWater;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "residents")
    private java.util.ArrayList<java.lang.String> relatedPeople;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "films")
    private java.util.ArrayList<java.lang.String> relatedFilms;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDiameter() {
        return null;
    }
    
    public final void setDiameter(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGravity() {
        return null;
    }
    
    public final void setGravity(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPopulation() {
        return null;
    }
    
    public final void setPopulation(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getClimate() {
        return null;
    }
    
    public final void setClimate(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTerrain() {
        return null;
    }
    
    public final void setTerrain(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getSubtitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRotationPeriod() {
        return null;
    }
    
    public final void setRotationPeriod(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOrbitalPeriod() {
        return null;
    }
    
    public final void setOrbitalPeriod(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSurfaceWater() {
        return null;
    }
    
    public final void setSurfaceWater(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedPeople() {
        return null;
    }
    
    public void setRelatedPeople(@org.jetbrains.annotations.Nullable()
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
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getRelatedPeopleTitle() {
        return null;
    }
    
    public Planet() {
        super();
    }
}