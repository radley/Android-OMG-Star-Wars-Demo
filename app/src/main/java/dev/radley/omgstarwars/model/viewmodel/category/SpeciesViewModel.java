package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class SpeciesViewModel extends ViewModel {

    private ArrayList<Species> mSpeciesList;
    private Call<SWModelList<Species>> mCall;
    private int mCount;
    private int mPage = 1;
    private String mQuery;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<Species>> mLiveData;

    public SpeciesViewModel() {
        mSpeciesList = new ArrayList<Species>();
        StarWarsApi.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<Species>> getSpecies(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<Species>>();

            if(query.equals("")) {

                //load asynchronously from server
                loadSpecies();
            } else {
                search(query);
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mSpeciesList.size() < mCount) {
            mPage++;
            loadSpecies();
        }
    }

    public String getCategoryId(int position) {
        return mSpeciesList.get(position).getCategoryId();
    }

    public Species getItem(int position) {
        return mSpeciesList.get(position);
    }

    protected void loadSpecies() {

        Log.d(Util.tag, "loadSpecies()");

        mCall = StarWarsApi.getApi().getAllSpecies(mPage);
        mCall.enqueue(new Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<Species> list) {

        Log.d(Util.tag, "onLoadSuccess()");

        if(mCount <= 0) {
            mCount = list.count;
        }

        for (Object object : list.results) {
            mSpeciesList.add(((Species) object));
        }

        mLiveData.setValue(mSpeciesList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCall != null && mCall.isExecuted())
            mCall.cancel();

        mSpeciesList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSpeciesList);
            return;
        }

        getSpeciesByPage(mPage);
    }

    protected void getSpeciesByPage(int page) {
        mCall = StarWarsApi.getApi().searchSpecies(mPage, mQuery);
        mCall.enqueue(new retrofit2.Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {

                onSpeciesSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onSpeciesSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mSpeciesList.add(((Species) object));
        }

        if(list.next != null) {
            mPage++;
            getSpeciesByPage(mPage);
        } else {
            mLiveData.setValue(mSpeciesList);
        }
    }
}
