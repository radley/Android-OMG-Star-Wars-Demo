package dev.radley.omgstarwars.ui.detail.related.old;


import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.util.Util;
import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.api.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

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

            Call<Planet> call = StarWarsService.getApiInstance().getPlanet(id);
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
                     Timber.e("error: " + t.getMessage());
                 }
            });
        }
    }
}
