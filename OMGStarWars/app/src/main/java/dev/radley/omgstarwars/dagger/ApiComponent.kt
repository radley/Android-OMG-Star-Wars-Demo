package dev.radley.omgstarwars.dagger


import dagger.Component
import dev.radley.omgstarwars.network.StarWarsService
import dev.radley.omgstarwars.viewmodels.CategoryViewModel
import dev.radley.omgstarwars.viewmodels.DetailViewModel
import dev.radley.omgstarwars.viewmodels.SearchViewModel
import javax.inject.Singleton


@Component(modules = [ApiModule::class])
interface ApiComponent {

    @Singleton
    fun inject(service: StarWarsService)

    fun inject(viewModel: CategoryViewModel)

    fun inject(viewModel: DetailViewModel)

    fun inject(viewModel: SearchViewModel)
}
