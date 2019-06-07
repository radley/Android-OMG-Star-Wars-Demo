package dev.radley.omgstarwars.model.viewmodel.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;

public abstract class CategoryViewModel extends ViewModel {

    protected ArrayList<SWModel> mSWModelList;
    protected int mCount = 0;
    protected int mPage = 1;
    protected String mQuery;

    //this is the data that we will fetch asynchronously
    protected MutableLiveData<ArrayList<SWModel>> mLiveData;

    public CategoryViewModel() {

        mSWModelList = new ArrayList<SWModel>();
        StarWarsApi.init();
    }

    public String getCategoryId(int position) {
        return mSWModelList.get(position).getCategoryId();
    }

    public int getCount() {
        return mCount;
    }

    public String getQuery() {
        return mQuery;
    }

    public abstract SWModel getItem(int position);
    protected abstract void loadByPage();
    protected abstract void search(String query);
    protected abstract void searchByPage();

    //we will call this method if we already have data
    public LiveData<ArrayList<SWModel>> getList(String query) {

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<SWModel>>();

            if(!query.equals("")) {
                search(query);

            } else {
                //loadByPage asynchronously from server
                loadByPage();
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mSWModelList.size() < mCount) {
            mPage++;

            if(mQuery != null) {
                searchByPage();
            } else {
                loadByPage();
            }

        }
    }

    protected void onSearchResponse(SWModelList list) {

        Log.d(Util.tag, "onSearchResponse(): " + mQuery);

        if(list == null) {
            Log.d(Util.tag, "onSearchResponse() - null list");
            mCount = 0;
            mPage = 1;
            mSWModelList.clear();
            mLiveData.setValue(mSWModelList);

        } else {
            mCount = list.count;
            Log.d(Util.tag, "onSearchResponse(): results = " + list.results.size());
            Log.d(Util.tag, "onSearchResponse(): count = " + mCount);

            for (Object object : list.results) {
                mSWModelList.add(((SWModel) object));
            }
            mLiveData.setValue(mSWModelList);
        }
    }

    protected void onSearchFailure() {

        mCount = -1;
        mPage = 1;
        mSWModelList.clear();
        mLiveData.setValue(mSWModelList);
    }

}
