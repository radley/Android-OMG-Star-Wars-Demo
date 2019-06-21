package dev.radley.omgstarwars.ui.detail.related.old;


import org.jetbrains.annotations.NotNull;

import dev.radley.omgstarwars.util.Util;
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.api.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class FilmsRowViewModel extends BaseRowViewModel {


    @Override
    protected void loadUrls() {

        for (int i = 0; i < mUrlList.size(); i++) {

            final int id = Util.getId(mUrlList.get(i));

            Call<Film> call = StarWarsService.getApiInstance().getFilm(id);
            call.enqueue(new Callback<Film>() {

                 @Override
                 public void onResponse(@NotNull Call<Film> call, @NotNull retrofit2.Response<Film> response) {

                     mSWModelList.add(response.body());

                     if(mSWModelList.size() == mUrlList.size()) {
                         mLiveData.setValue(mSWModelList);
                     }
                 }

                 @Override
                 public void onFailure(@NotNull Call<Film> call, @NotNull Throwable t) {
                     Timber.e("error: " + t.getMessage());
                 }
            });
        }
    }
}
