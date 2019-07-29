package dev.radley.omgstarwars.view;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\fH\u0002J\b\u0010\u000e\u001a\u00020\fH\u0002J\b\u0010\u000f\u001a\u00020\fH\u0002J\b\u0010\u0010\u001a\u00020\fH\u0002J\b\u0010\u0011\u001a\u00020\fH\u0002J\b\u0010\u0012\u001a\u00020\fH\u0002J\b\u0010\u0013\u001a\u00020\fH\u0002J\b\u0010\u0014\u001a\u00020\fH\u0002J\b\u0010\u0015\u001a\u00020\fH\u0002J\b\u0010\u0016\u001a\u00020\fH\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0012\u0010\u001e\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010!\u001a\u00020\fH\u0016J\b\u0010\"\u001a\u00020\fH\u0002J\u0010\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020%H\u0002J\n\u0010&\u001a\u00020\'*\u00020(R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Ldev/radley/omgstarwars/view/DetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "descriptionLayout", "Ldev/radley/omgstarwars/view/detail/description/DescriptionLayout;", "heroLayout", "Ldev/radley/omgstarwars/view/detail/hero/HeroLayout;", "layout", "Landroid/widget/LinearLayout;", "viewModel", "Ldev/radley/omgstarwars/viewmodels/DetailViewModel;", "addDescriptionLayout", "", "addHeroLayout", "addHomeWorldTextLink", "addRelatedFilms", "addRelatedLists", "addRelatedPeople", "addRelatedPlanets", "addRelatedSpecies", "addRelatedStarships", "addRelatedVehicles", "addSpeciesTextLink", "dpToPx", "", "dp", "getRelatedListView", "Landroidx/recyclerview/widget/RecyclerView;", "title", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setupToolbar", "startDetailActivity", "item", "Ldev/radley/omgstarwars/models/SWModel;", "getLocationOnScreen", "Landroid/graphics/Point;", "Landroid/view/View;", "app_debug"})
public final class DetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.LinearLayout layout;
    private dev.radley.omgstarwars.view.detail.description.DescriptionLayout descriptionLayout;
    private dev.radley.omgstarwars.view.detail.hero.HeroLayout heroLayout;
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
    
    private final void addHeroLayout() {
    }
    
    public final int dpToPx(int dp) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Point getLocationOnScreen(@org.jetbrains.annotations.NotNull()
    android.view.View $this$getLocationOnScreen) {
        return null;
    }
    
    private final void addDescriptionLayout() {
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
    
    private final androidx.recyclerview.widget.RecyclerView getRelatedListView(java.lang.String title) {
        return null;
    }
    
    public DetailActivity() {
        super();
    }
}