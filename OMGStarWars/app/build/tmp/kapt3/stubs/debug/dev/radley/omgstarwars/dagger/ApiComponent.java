package dev.radley.omgstarwars.dagger;

import java.lang.System;

/**
 * Injectors
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\bH&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Ldev/radley/omgstarwars/dagger/ApiComponent;", "", "inject", "", "service", "Ldev/radley/omgstarwars/network/StarWarsService;", "viewModel", "Ldev/radley/omgstarwars/viewmodels/CategoryViewModel;", "Ldev/radley/omgstarwars/viewmodels/DetailViewModel;", "Ldev/radley/omgstarwars/viewmodels/SearchViewModel;", "app_debug"})
@dagger.Component(modules = {dev.radley.omgstarwars.dagger.ApiModule.class})
public abstract interface ApiComponent {
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.network.StarWarsService service);
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.viewmodels.CategoryViewModel viewModel);
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.viewmodels.DetailViewModel viewModel);
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.viewmodels.SearchViewModel viewModel);
}