package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * Film model represents a Star Wars single film.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0019\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0010\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR&\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00148\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR&\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00148\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u0018R\u0014\u0010\u001f\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0007R&\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00148\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0016\"\u0004\b#\u0010\u0018R&\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00148\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0016\"\u0004\b&\u0010\u0018R&\u0010\'\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00148\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0016\"\u0004\b)\u0010\u0018R\u001a\u0010*\u001a\u00020\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0007\"\u0004\b,\u0010\t\u00a8\u0006-"}, d2 = {"Ldev/radley/omgstarwars/models/Film;", "Ldev/radley/omgstarwars/models/SWModel;", "Ljava/io/Serializable;", "()V", "director", "", "getDirector", "()Ljava/lang/String;", "setDirector", "(Ljava/lang/String;)V", "episodeId", "", "getEpisodeId", "()I", "setEpisodeId", "(I)V", "openingCrawl", "getOpeningCrawl", "setOpeningCrawl", "planets", "Ljava/util/ArrayList;", "getPlanets", "()Ljava/util/ArrayList;", "setPlanets", "(Ljava/util/ArrayList;)V", "producer", "getProducer", "setProducer", "relatedPeople", "getRelatedPeople", "setRelatedPeople", "relatedPeopleTitle", "getRelatedPeopleTitle", "relatedSpecies", "getRelatedSpecies", "setRelatedSpecies", "relatedStarships", "getRelatedStarships", "setRelatedStarships", "relatedVehicles", "getRelatedVehicles", "setRelatedVehicles", "title", "getTitle", "setTitle", "app_debug"})
public final class Film extends dev.radley.omgstarwars.models.SWModel implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String title;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String director;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String producer;
    @com.google.gson.annotations.SerializedName(value = "episode_id")
    private int episodeId;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "opening_crawl")
    private java.lang.String openingCrawl;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "species")
    private java.util.ArrayList<java.lang.String> relatedSpecies;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "starships")
    private java.util.ArrayList<java.lang.String> relatedStarships;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "vehicles")
    private java.util.ArrayList<java.lang.String> relatedVehicles;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "planets")
    private java.util.ArrayList<java.lang.String> planets;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "characters")
    private java.util.ArrayList<java.lang.String> relatedPeople;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getTitle() {
        return null;
    }
    
    public void setTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDirector() {
        return null;
    }
    
    public final void setDirector(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProducer() {
        return null;
    }
    
    public final void setProducer(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getEpisodeId() {
        return 0;
    }
    
    public final void setEpisodeId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOpeningCrawl() {
        return null;
    }
    
    public final void setOpeningCrawl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
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
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getPlanets() {
        return null;
    }
    
    public void setPlanets(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.util.ArrayList<java.lang.String> getRelatedPeople() {
        return null;
    }
    
    public void setRelatedPeople(@org.jetbrains.annotations.Nullable()
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getRelatedPeopleTitle() {
        return null;
    }
    
    public Film() {
        super();
    }
}