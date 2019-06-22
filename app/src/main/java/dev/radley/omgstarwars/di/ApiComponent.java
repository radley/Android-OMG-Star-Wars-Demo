package dev.radley.omgstarwars.di;


import dagger.Component;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.viewmodels.CategoryViewModel;
import dev.radley.omgstarwars.viewmodels.DetailViewModel;
import dev.radley.omgstarwars.viewmodels.SearchViewModel;


@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(StarWarsService service);

    void inject(CategoryViewModel viewModel);

    void inject(DetailViewModel viewModel);

    void inject(SearchViewModel viewModel);
}
