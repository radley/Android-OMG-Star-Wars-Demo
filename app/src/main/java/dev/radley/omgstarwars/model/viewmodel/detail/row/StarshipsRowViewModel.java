package dev.radley.omgstarwars.model.viewmodel.detail.row;


import android.util.Log;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class StarshipsRowViewModel extends BaseRowViewModel {


    @Override
    public int getPlaceholderRes() {
        return R.drawable.placeholder_wide;
    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_starship;
    }

    @Override
    protected void loadUrls() {

        for (int i = 0; i < mUrlList.size(); i++) {

            final int id = Util.getId(mUrlList.get(i));

            Call<Starship> call = StarWarsApi.getApi().getStarship(id);
            call.enqueue(new Callback<Starship>() {

                 @Override
                 public void onResponse(Call<Starship> call, retrofit2.Response<Starship> response) {

                     mSWModelList.add(response.body());

                     if(mSWModelList.size() == mUrlList.size()) {
                         mLiveData.setValue(mSWModelList);
                     }
                 }

                 @Override
                 public void onFailure(Call<Starship> call, Throwable t) {
                     Log.d(Util.tag, "error: " + t.getMessage());
                 }
            });
        }
    }
}
