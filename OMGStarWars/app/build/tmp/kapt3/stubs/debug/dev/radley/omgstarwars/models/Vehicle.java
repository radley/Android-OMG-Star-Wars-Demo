package dev.radley.omgstarwars.models;

import java.lang.System;

/**
 * Vehicle model represents a single transport craft that does not have hyperdrive capability.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\r\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001e\u0010\r\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR\u001e\u0010\u0019\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR\u001a\u0010\u001c\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR\u001a\u0010\u001f\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0007\"\u0004\b!\u0010\tR&\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010#8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R&\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010#8\u0016@\u0016X\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010%\"\u0004\b*\u0010\'R\u0014\u0010+\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010\u0007R\u001e\u0010-\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0007\"\u0004\b/\u0010\t\u00a8\u00060"}, d2 = {"Ldev/radley/omgstarwars/models/Vehicle;", "Ldev/radley/omgstarwars/models/SWModel;", "Ljava/io/Serializable;", "()V", "cargoCapacity", "", "getCargoCapacity", "()Ljava/lang/String;", "setCargoCapacity", "(Ljava/lang/String;)V", "consumables", "getConsumables", "setConsumables", "costInCredits", "getCostInCredits", "setCostInCredits", "crew", "getCrew", "setCrew", "length", "getLength", "setLength", "manufacturer", "getManufacturer", "setManufacturer", "maxAtmospheringSpeed", "getMaxAtmospheringSpeed", "setMaxAtmospheringSpeed", "model", "getModel", "setModel", "passengers", "getPassengers", "setPassengers", "relatedFilms", "Ljava/util/ArrayList;", "getRelatedFilms", "()Ljava/util/ArrayList;", "setRelatedFilms", "(Ljava/util/ArrayList;)V", "relatedPeople", "getRelatedPeople", "setRelatedPeople", "relatedPeopleTitle", "getRelatedPeopleTitle", "vehicleClass", "getVehicleClass", "setVehicleClass", "app_debug"})
public class Vehicle extends dev.radley.omgstarwars.models.SWModel implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String model;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String manufacturer;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String length;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String crew;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String passengers;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String consumables;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "vehicle_class")
    private java.lang.String vehicleClass;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "cost_in_credits")
    private java.lang.String costInCredits;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "max_atmosphering_speed")
    private java.lang.String maxAtmospheringSpeed;
    @org.jetbrains.annotations.NotNull()
    @com.google.gson.annotations.SerializedName(value = "cargo_capacity")
    private java.lang.String cargoCapacity;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "pilots")
    private java.util.ArrayList<java.lang.String> relatedPeople;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "films")
    private java.util.ArrayList<java.lang.String> relatedFilms;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getModel() {
        return null;
    }
    
    public final void setModel(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getManufacturer() {
        return null;
    }
    
    public final void setManufacturer(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLength() {
        return null;
    }
    
    public final void setLength(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCrew() {
        return null;
    }
    
    public final void setCrew(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPassengers() {
        return null;
    }
    
    public final void setPassengers(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConsumables() {
        return null;
    }
    
    public final void setConsumables(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVehicleClass() {
        return null;
    }
    
    public final void setVehicleClass(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCostInCredits() {
        return null;
    }
    
    public final void setCostInCredits(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMaxAtmospheringSpeed() {
        return null;
    }
    
    public final void setMaxAtmospheringSpeed(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCargoCapacity() {
        return null;
    }
    
    public final void setCargoCapacity(@org.jetbrains.annotations.NotNull()
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
    
    public Vehicle() {
        super();
    }
}