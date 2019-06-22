package dev.radley.omgstarwars.old.category.viewmodels;

import dev.radley.omgstarwars.data.SWModelList;
import dev.radley.omgstarwars.data.Vehicle;
import dev.radley.omgstarwars.network.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class VehiclesViewModel extends BaseCategoryViewModel {
    
    private Call<SWModelList<Vehicle>> mCallVehicles;

    public Vehicle getItem(int position) {
        return (Vehicle) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallVehicles = service.getApi().getAllVehicles(mPage);
        mCallVehicles.enqueue(new Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Timber.d("error: " + t.getMessage());
                resetPageCounter();
            }
        });
    }

    protected void onLoadResponse(SWModelList<Vehicle> list) {

        if(list != null) {
            mCount = list.count;

            mSWModelList.addAll(list.results);

            if(list.next != null) {
                mPage = getIntFromNextPageUrl(list.next);
            } else {
                resetPageCounter();
            }

            mLiveData.setValue(mSWModelList);
        }
    }

    public void search(String query) {

        mQuery = query;

        if(mCallVehicles != null && mCallVehicles.isExecuted())
            mCallVehicles.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallVehicles = service.getApi().searchVehicles(mPage, mQuery);
        mCallVehicles.enqueue(new retrofit2.Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Timber.e("error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
