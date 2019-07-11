package dev.radley.omgstarwars.view.detailview;

import java.lang.System;

/**
 * Layout for Planet details text
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0002R\u001a\u0010\u0005\u001a\u00020\bX\u0080.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2 = {"Ldev/radley/omgstarwars/view/detailview/PlanetDetailView;", "Ldev/radley/omgstarwars/view/detailview/DetailView;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "model", "Ldev/radley/omgstarwars/models/SWModel;", "(Landroid/content/Context;Ldev/radley/omgstarwars/models/SWModel;)V", "Ldev/radley/omgstarwars/models/Planet;", "getModel$app_debug", "()Ldev/radley/omgstarwars/models/Planet;", "setModel$app_debug", "(Ldev/radley/omgstarwars/models/Planet;)V", "populateContent", "", "app_debug"})
public final class PlanetDetailView extends dev.radley.omgstarwars.view.detailview.DetailView {
    @org.jetbrains.annotations.NotNull()
    public dev.radley.omgstarwars.models.Planet model;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.models.Planet getModel$app_debug() {
        return null;
    }
    
    public final void setModel$app_debug(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.models.Planet p0) {
    }
    
    /**
     * Add content to layout
     */
    private final void populateContent() {
    }
    
    public PlanetDetailView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    public PlanetDetailView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.models.SWModel model) {
        super(null);
    }
}