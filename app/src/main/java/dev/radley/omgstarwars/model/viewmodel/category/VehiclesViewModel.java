package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.model.sw.Vehicle;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class VehiclesViewModel extends ViewModel {

    private ArrayList<Vehicle> mVehiclesList;
    private Call<SWModelList<Vehicle>> mCall;
    private int mCount;
    private int mPage = 1;
    private String mQuery;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<Vehicle>> mLiveData;

    public VehiclesViewModel() {
        mVehiclesList = new ArrayList<Vehicle>();
        StarWarsApi.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<Vehicle>> getVehicles(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<Vehicle>>();

            if(query.equals("")) {

                //load asynchronously from server
                loadVehicles();
            } else {
                search(query);
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mVehiclesList.size() < mCount) {
            mPage++;
            loadVehicles();
        }
    }

    public String getCategoryId(int position) {
        return mVehiclesList.get(position).getCategoryId();
    }

    public Vehicle getVehicle(int position) {
        return mVehiclesList.get(position);
    }

    protected void loadVehicles() {

        Log.d(Util.tag, "loadVehicles()");

        mCall = StarWarsApi.getApi().getAllVehicles(mPage);
        mCall.enqueue(new Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<Vehicle> list) {

        Log.d(Util.tag, "onLoadSuccess()");

        if(mCount <= 0) {
            mCount = list.count;
        }

        for (Object object : list.results) {
            mVehiclesList.add(((Vehicle) object));
        }

        mLiveData.setValue(mVehiclesList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCall != null && mCall.isExecuted())
            mCall.cancel();

        mVehiclesList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mVehiclesList);
            return;
        }

        getVehiclesByPage(mPage);
    }

    protected void getVehiclesByPage(int page) {
        mCall = StarWarsApi.getApi().searchVehicles(mPage, mQuery);
        mCall.enqueue(new retrofit2.Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {

                onVehiclesSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onVehiclesSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mVehiclesList.add(((Vehicle) object));
        }

        if(list.next != null) {
            mPage++;
            getVehiclesByPage(mPage);
        } else {
            mLiveData.setValue(mVehiclesList);
        }
    }
}
