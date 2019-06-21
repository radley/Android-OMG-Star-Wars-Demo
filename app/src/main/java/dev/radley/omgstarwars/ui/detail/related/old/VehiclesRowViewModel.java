package dev.radley.omgstarwars.ui.detail.related.old;


import org.jetbrains.annotations.NotNull;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.util.Util;
import dev.radley.omgstarwars.network.model.Vehicle;
import dev.radley.omgstarwars.network.api.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class VehiclesRowViewModel extends BaseRowViewModel {


    @Override
    public int getPlaceholderRes() {
        return R.drawable.placeholder_wide;
    }

    @Override
    public int getFallbackRes() {
        return R.drawable.generic_vehicle;
    }

    @Override
    protected void loadUrls() {

        for (int i = 0; i < mUrlList.size(); i++) {

            final int id = Util.getId(mUrlList.get(i));

            Call<Vehicle> call = StarWarsService.getApiInstance().getVehicle(id);
            call.enqueue(new Callback<Vehicle>() {

                 @Override
                 public void onResponse(@NotNull Call<Vehicle> call, @NotNull retrofit2.Response<Vehicle> response) {

                     mSWModelList.add(response.body());

                     if(mSWModelList.size() == mUrlList.size()) {
                         mLiveData.setValue(mSWModelList);
                     }
                 }

                 @Override
                 public void onFailure(@NotNull Call<Vehicle> call, @NotNull Throwable t) {
                     Timber.e("error: %s", t.getMessage());
                 }
            });
        }
    }
}
