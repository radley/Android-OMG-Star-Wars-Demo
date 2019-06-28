package dev.radley.omgstarwars.dagger;


import javax.inject.Singleton;

import dagger.Component;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.viewmodels.CategoryViewModel;
import dev.radley.omgstarwars.viewmodels.DetailViewModel;
import dev.radley.omgstarwars.viewmodels.SearchViewModel;


/**
 * Injectors
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(StarWarsService service);

    void inject(CategoryViewModel viewModel);

    void inject(DetailViewModel viewModel);

    void inject(SearchViewModel viewModel);
}
