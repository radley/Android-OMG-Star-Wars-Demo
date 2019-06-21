package dev.radley.omgstarwars.ui.detail.related.old;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.api.StarWarsService;


public abstract class BaseRowViewModel extends ViewModel {

    protected ArrayList<SWModel> mSWModelList;
    protected ArrayList<String> mUrlList;

    //this is the data that we will fetch asynchronously
    protected MutableLiveData<ArrayList<SWModel>> mLiveData;

    public BaseRowViewModel() {

        mSWModelList = new ArrayList<SWModel>();
        StarWarsService.init();
    }

    //we will call this method if we already have data
    public LiveData<ArrayList<SWModel>> getList(ArrayList<String> urlList) {

        mUrlList = urlList;

        //if the list is null
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<SWModel>>();

            loadUrls();
        }

        //finally we will return the list
        return mLiveData;
    }

    public int getPlaceholderRes() {
        return R.drawable.placeholder_tall;
    }

    public int getFallbackRes() {
        return R.drawable.placeholder_tall;
    }

    protected abstract void loadUrls();

}
