package dev.radley.omgstarwars.dagger;

import java.lang.System;

/**
 * Modules
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0001\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\b\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Ldev/radley/omgstarwars/dagger/ApiModule;", "", "()V", "provideStarWarsApi", "Ldev/radley/omgstarwars/network/StarWarsApi;", "provideStarWarsService", "Ldev/radley/omgstarwars/network/StarWarsService;", "app_debug"})
@dagger.Module()
public final class ApiModule {
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final dev.radley.omgstarwars.network.StarWarsApi provideStarWarsApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final dev.radley.omgstarwars.network.StarWarsService provideStarWarsService() {
        return null;
    }
    
    public ApiModule() {
        super();
    }
}