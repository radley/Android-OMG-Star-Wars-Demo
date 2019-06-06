package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class StarshipsViewModel extends ViewModel {

    private ArrayList<Starship> mStarshipsList;
    private Call<SWModelList<Starship>> mCall;
    private int mCount;
    private int mPage = 1;
    private String mQuery;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<Starship>> mLiveData;

    public StarshipsViewModel() {
        mStarshipsList = new ArrayList<Starship>();
        StarWarsApi.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<Starship>> getStarships(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<Starship>>();

            if(query.equals("")) {

                //load asynchronously from server
                loadStarships();
            } else {
                search(query);
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mStarshipsList.size() < mCount) {
            mPage++;
            loadStarships();
        }
    }

    public String getCategoryId(int position) {
        return mStarshipsList.get(position).getCategoryId();
    }

    public Starship getItem(int position) {
        return mStarshipsList.get(position);
    }
    
    protected void loadStarships() {

        Log.d(Util.tag, "loadStarships()");

        mCall = StarWarsApi.getApi().getAllStarships(mPage);
        mCall.enqueue(new Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<Starship> list) {

        Log.d(Util.tag, "onLoadSuccess()");

        if(mCount <= 0) {
            mCount = list.count;
        }

        for (Object object : list.results) {
            mStarshipsList.add(((Starship) object));
        }

        mLiveData.setValue(mStarshipsList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCall != null && mCall.isExecuted())
            mCall.cancel();

        mStarshipsList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mStarshipsList);
            return;
        }

        getStarshipsByPage(mPage);
    }

    protected void getStarshipsByPage(int page) {
        mCall = StarWarsApi.getApi().searchStarships(mPage, mQuery);
        mCall.enqueue(new retrofit2.Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {

                onStarshipsSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onStarshipsSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mStarshipsList.add(((Starship) object));
        }

        if(list.next != null) {
            mPage++;
            getStarshipsByPage(mPage);
        } else {
            mLiveData.setValue(mStarshipsList);
        }
    }
}
