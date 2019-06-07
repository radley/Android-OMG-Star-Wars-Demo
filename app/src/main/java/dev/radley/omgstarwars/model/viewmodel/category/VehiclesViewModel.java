package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.model.sw.Vehicle;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class VehiclesViewModel extends CategoryViewModel {
    
    private Call<SWModelList<Vehicle>> mCallVehicles;

    public Vehicle getItem(int position) {
        return (Vehicle) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallVehicles = StarWarsApi.getApi().getAllVehicles(mPage);
        mCallVehicles.enqueue(new Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadResponse(SWModelList<Vehicle> list) {

        if(list != null) {
            mCount = list.count;

            for (Object object : list.results) {
                mSWModelList.add(((Vehicle) object));
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
        mCallVehicles = StarWarsApi.getApi().searchVehicles(mPage, mQuery);
        mCallVehicles.enqueue(new retrofit2.Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
