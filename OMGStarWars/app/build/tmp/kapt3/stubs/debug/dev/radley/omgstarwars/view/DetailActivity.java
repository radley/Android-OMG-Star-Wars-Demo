package dev.radley.omgstarwars.view;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002J\b\u0010\f\u001a\u00020\nH\u0002J\b\u0010\r\u001a\u00020\nH\u0002J\b\u0010\u000e\u001a\u00020\nH\u0002J\b\u0010\u000f\u001a\u00020\nH\u0002J\b\u0010\u0010\u001a\u00020\nH\u0002J\b\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\nH\u0002J\b\u0010\u0013\u001a\u00020\nH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\nH\u0016J\b\u0010\u001c\u001a\u00020\nH\u0002J\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Ldev/radley/omgstarwars/view/DetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "detailView", "Ldev/radley/omgstarwars/view/detailview/DetailView;", "layout", "Landroid/widget/LinearLayout;", "viewModel", "Ldev/radley/omgstarwars/viewmodels/DetailViewModel;", "addDetailView", "", "addHomeWorldTextLink", "addRelatedFilms", "addRelatedLists", "addRelatedPeople", "addRelatedPlanets", "addRelatedSpecies", "addRelatedStarships", "addRelatedVehicles", "addSpeciesTextLink", "getRelatedListView", "Landroidx/recyclerview/widget/RecyclerView;", "title", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setupToolbar", "startDetailActivity", "item", "Ldev/radley/omgstarwars/models/SWModel;", "updateHeroImage", "imagePath", "fallback", "", "app_debug"})
public final class DetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.LinearLayout layout;
    private dev.radley.omgstarwars.view.detailview.DetailView detailView;
    private dev.radley.omgstarwars.viewmodels.DetailViewModel viewModel;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void setupToolbar() {
    }
    
    private final void addDetailView() {
    }
    
    private final void addRelatedLists() {
    }
    
    private final void addRelatedFilms() {
    }
    
    private final void addRelatedPeople() {
    }
    
    private final void addRelatedPlanets() {
    }
    
    private final void addRelatedSpecies() {
    }
    
    private final void addRelatedStarships() {
    }
    
    private final void addRelatedVehicles() {
    }
    
    private final void addHomeWorldTextLink() {
    }
    
    private final void addSpeciesTextLink() {
    }
    
    private final void startDetailActivity(dev.radley.omgstarwars.models.SWModel item) {
    }
    
    private final void updateHeroImage(java.lang.String imagePath, int fallback) {
    }
    
    private final androidx.recyclerview.widget.RecyclerView getRelatedListView(java.lang.String title) {
        return null;
    }
    
    public DetailActivity() {
        super();
    }
}