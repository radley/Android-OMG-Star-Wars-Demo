package dev.radley.omgstarwars.utilities;

import java.lang.System;

/**
 * Sorting utilities
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\t\u001a\u00020\nJ$\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0004\u00a8\u0006\r"}, d2 = {"Ldev/radley/omgstarwars/utilities/SortUtils;", "", "()V", "sortFilmsByEpisode", "Ljava/util/ArrayList;", "Ldev/radley/omgstarwars/models/Film;", "list", "sortForBestQueryMatch", "Ldev/radley/omgstarwars/models/SWModel;", "query", "", "sortForStarships", "sortSWModelByEpisode", "app_debug"})
public final class SortUtils {
    public static final dev.radley.omgstarwars.utilities.SortUtils INSTANCE = null;
    
    /**
     * Sort films based on release date
     *
     * @param list
     * @return sorted list
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<dev.radley.omgstarwars.models.Film> sortFilmsByEpisode(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<dev.radley.omgstarwars.models.Film> list) {
        return null;
    }
    
    /**
     * Sort films based on release date
     *
     * @param list
     * @return sorted list
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> sortSWModelByEpisode(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> list) {
        return null;
    }
    
    /**
     * A series of sorting filters that alphabetizes and then lists results in this order:
     *
     * 1. Explicit query string match (i.e for "star" query -> "star destroyer", "death star")
     * - sort by index position (i.e "star destroyer" before "death star")
     *
     * 2. Title contains query string (i.e "starfighter", "starship")
     * - sort by index position (i.e "jedi starfighter" before "naboo royal starship")
     *
     * 3. Starship special case (swapi also searches in Starship.model)
     * a. Explicit query string match in .model
     * b. .model title contains query string
     *
     * 4. anything left over (shouldn't happen, but future proof)
     *
     * @param list ArrayList<SWModel>
     * @param query String
     * @return sorted list
     *   </SWModel>
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> sortForBestQueryMatch(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> list, @org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    /**
     * Sorting specific to `Starship.model` value
     *
     * @param list ArrayList<SWModel>
     * @param query String
     * @return sorted ArrayList<SWModel>
     *   </SWModel></SWModel>
     */
    private final java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> sortForStarships(java.util.ArrayList<dev.radley.omgstarwars.models.SWModel> list, java.lang.String query) {
        return null;
    }
    
    private SortUtils() {
        super();
    }
}