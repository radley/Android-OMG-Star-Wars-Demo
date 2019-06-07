package dev.radley.omgstarwars.model.viewmodel.detail.row;


import android.util.Log;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class PlanetsRowViewModel extends BaseRowViewModel {


    @Override
    public int getPlaceholderRes() {
        return R.drawable.placeholder_square;
    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_planet;
    }

    @Override
    protected void loadUrls() {

        for (int i = 0; i < mUrlList.size(); i++) {

            final int id = Util.getId(mUrlList.get(i));

            Call<Planet> call = StarWarsApi.getApi().getPlanet(id);
            call.enqueue(new Callback<Planet>() {

                 @Override
                 public void onResponse(Call<Planet> call, retrofit2.Response<Planet> response) {

                     mSWModelList.add(response.body());

                     if(mSWModelList.size() == mUrlList.size()) {
                         mLiveData.setValue(mSWModelList);
                     }
                 }

                 @Override
                 public void onFailure(Call<Planet> call, Throwable t) {
                     Log.d(Util.tag, "error: " + t.getMessage());
                 }
            });
        }
    }
}
