package dev.radley.omgstarwars.network;

import java.lang.System;

/**
 * Service for loadind data from StarWars API service
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Ldev/radley/omgstarwars/network/StarWarsService;", "", "()V", "api", "Ldev/radley/omgstarwars/network/StarWarsApi;", "getApi", "()Ldev/radley/omgstarwars/network/StarWarsApi;", "setApi", "(Ldev/radley/omgstarwars/network/StarWarsApi;)V", "app_debug"})
@javax.inject.Singleton()
public final class StarWarsService {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public dev.radley.omgstarwars.network.StarWarsApi api;
    
    @org.jetbrains.annotations.NotNull()
    public final dev.radley.omgstarwars.network.StarWarsApi getApi() {
        return null;
    }
    
    public final void setApi(@org.jetbrains.annotations.NotNull()
    dev.radley.omgstarwars.network.StarWarsApi p0) {
    }
    
    public StarWarsService() {
        super();
    }
}