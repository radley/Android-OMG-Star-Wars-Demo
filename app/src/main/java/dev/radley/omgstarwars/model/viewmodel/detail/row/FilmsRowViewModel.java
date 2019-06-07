package dev.radley.omgstarwars.model.viewmodel.detail.row;


import android.util.Log;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class FilmsRowViewModel extends BaseRowViewModel {


    @Override
    protected void loadUrls() {

        for (int i = 0; i < mUrlList.size(); i++) {

            final int id = Util.getId(mUrlList.get(i));

            Call<Film> call = StarWarsApi.getApi().getFilm(id);
            call.enqueue(new Callback<Film>() {

                 @Override
                 public void onResponse(Call<Film> call, retrofit2.Response<Film> response) {

                     mSWModelList.add(response.body());

                     if(mSWModelList.size() == mUrlList.size()) {
                         mLiveData.setValue(mSWModelList);
                     }
                 }

                 @Override
                 public void onFailure(Call<Film> call, Throwable t) {
                     Log.d(Util.tag, "error: " + t.getMessage());
                 }
            });
        }
    }
}
