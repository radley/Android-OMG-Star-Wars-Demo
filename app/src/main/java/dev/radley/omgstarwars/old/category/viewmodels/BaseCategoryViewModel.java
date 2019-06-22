package dev.radley.omgstarwars.old.category.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Set;

import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.data.SWModelList;
import dev.radley.omgstarwars.network.StarWarsService;
import dev.radley.omgstarwars.utilities.SortUtil;

public abstract class BaseCategoryViewModel extends ViewModel {


    protected int DEFAULT_PAGE = 1;
    protected ArrayList<SWModel> mSWModelList;
    protected int mCount = 0;
    protected int mPage = DEFAULT_PAGE;
    protected String mQuery;

    protected StarWarsService service;

    //this is the data that we will fetch asynchronously
    protected MutableLiveData<ArrayList<SWModel>> mLiveData;

    public BaseCategoryViewModel() {

        mSWModelList = new ArrayList<SWModel>();
        service = new StarWarsService();
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


    public LiveData<ArrayList<SWModel>> getList(String query) {

        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<SWModel>>();

            if(!query.equals("")) {
                search(query);
            } else {
                loadByPage();
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mPage > DEFAULT_PAGE) {
            if(mQuery != null) {
                searchByPage();
            } else {
                loadByPage();
            }
        }
    }

    protected int getIntFromNextPageUrl(String url) {

        Uri uri = Uri.parse(url);
        Set<String> args = uri.getQueryParameterNames();

        if(uri.getQueryParameter("page") != null) {
            return Integer.parseInt(uri.getQueryParameter("page"));
        }

        // default
        return DEFAULT_PAGE;
    }

    protected void resetPageCounter() {
        mPage = DEFAULT_PAGE;
    }

    protected void onSearchResponse(SWModelList list) {

        // empty list
        if(list == null || list.count == 0) {
            mCount = 0;
            resetPageCounter();
            mSWModelList.clear();
            mLiveData.setValue(mSWModelList);

        } else {

            mCount = list.count;

            for (Object object : list.results) {
                mSWModelList.add(((SWModel) object));
            }

            // Loop until we get all search results
            // because swapi paged results are not optimized
            // and best results can be on 2nd page (or later)
            // i.e. "Death Star" for "star" search is on page 2!
            //
            if(list.next != null) {
                mPage = getIntFromNextPageUrl(list.next);
                searchByPage();
                return;
            }

            resetPageCounter();

            ArrayList<SWModel> sortList = SortUtil.sortForBestQueryMatch(mSWModelList, mQuery);

            // must keep adapter reference
            mSWModelList.clear();
            mSWModelList.addAll(sortList);

            mLiveData.setValue(mSWModelList);
        }
    }

    protected void onSearchFailure() {

        mCount = -1; // error state
        resetPageCounter();
        mSWModelList.clear();
        mLiveData.setValue(mSWModelList);
    }


}
