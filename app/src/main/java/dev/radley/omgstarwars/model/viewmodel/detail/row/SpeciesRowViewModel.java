package dev.radley.omgstarwars.model.viewmodel.detail.row;


import android.util.Log;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class SpeciesRowViewModel extends BaseRowViewModel {


//    @Override
//    public int getPlaceholderRes() {
//        return R.drawable.placeholder_tall;
//    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_species;
    }

    @Override
    protected void loadUrls() {

        for (int i = 0; i < mUrlList.size(); i++) {

            final int id = Util.getId(mUrlList.get(i));

            Call<Species> call = StarWarsApi.getApi().getSpecies(id);
            call.enqueue(new Callback<Species>() {

                 @Override
                 public void onResponse(Call<Species> call, retrofit2.Response<Species> response) {

                     mSWModelList.add(response.body());

                     if(mSWModelList.size() == mUrlList.size()) {
                         mLiveData.setValue(mSWModelList);
                     }
                 }

                 @Override
                 public void onFailure(Call<Species> call, Throwable t) {
                     Log.d(Util.tag, "error: " + t.getMessage());
                 }
            });
        }
    }
}
