package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class PeopleViewModel extends ViewModel {

    private ArrayList<People> mPeopleList;
    private Call<SWModelList<People>> mCall;
    private int mCount;
    private int mPage = 1;
    private String mQuery;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ArrayList<People>> mLiveData;

    public PeopleViewModel() {
        mPeopleList = new ArrayList<People>();
        StarWarsApi.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<People>> getPeople(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<People>>();

            if(query.equals("")) {

                //load asynchronously from server
                loadPeople();
            } else {
                search(query);
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mPeopleList.size() < mCount) {
            mPage++;
            loadPeople();
        }
    }

    public String getCategoryId(int position) {
        return mPeopleList.get(position).getCategoryId();
    }

    public People getItem(int position) {
        return mPeopleList.get(position);
    }

    protected void loadPeople() {

        Log.d(Util.tag, "loadPeople()");

        mCall = StarWarsApi.getApi().getAllPeople(mPage);
        mCall.enqueue(new Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {
                onLoadSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onLoadSuccess(SWModelList<People> list) {

        Log.d(Util.tag, "onLoadSuccess()");

        if(mCount <= 0) {
            mCount = list.count;
        }

        for (Object object : list.results) {
            mPeopleList.add(((People) object));
        }

        mLiveData.setValue(mPeopleList);
    }

    public void search(String query) {

        mQuery = query;

        if(mCall != null && mCall.isExecuted())
            mCall.cancel();

        mPeopleList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mPeopleList);
            return;
        }

        getPeopleByPage(mPage);
    }

    protected void getPeopleByPage(int page) {
        mCall = StarWarsApi.getApi().searchPeople(mPage, mQuery);
        mCall.enqueue(new retrofit2.Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {

                onPeopleSearchSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Log.d(Util.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onPeopleSearchSuccess(SWModelList list) {

        for (Object object : list.results) {
            mPeopleList.add(((People) object));
        }

        if(list.next != null) {
            mPage++;
            getPeopleByPage(mPage);
        } else {
            mLiveData.setValue(mPeopleList);
        }
    }
}
