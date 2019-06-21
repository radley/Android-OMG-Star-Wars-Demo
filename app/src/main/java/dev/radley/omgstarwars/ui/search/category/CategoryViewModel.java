package dev.radley.omgstarwars.ui.search.category;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.network.model.SWModelList;
import dev.radley.omgstarwars.util.SortUtil;
import dev.radley.omgstarwars.network.api.ApiBatcher;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class CategoryViewModel extends AndroidViewModel {


    private ArrayList<SWModel> mSWModelList = new ArrayList<SWModel>();
    private MutableLiveData<ArrayList<SWModel>> mLiveData;

    private CompositeDisposable mDisposable;

    private int DEFAULT_PAGE = 1;
    private int mCount = 0;
    private int mNext = DEFAULT_PAGE;
    private Observer mObserver;
    private String mQuery;
    private String mId;



    public CategoryViewModel(Application app, String id) {
        super(app);

        mId = id;
        setupObserver();
    }


    public LiveData<ArrayList<SWModel>> getList(String query) {

        Timber.d("getList(" +query+")");

        if (mLiveData == null) {
            mLiveData = new MutableLiveData<ArrayList<SWModel>>();

            if(!query.equals("")) {
                search(mQuery);
            } else {
                loadData();
            }
        }

        //finally we will return the list
        return mLiveData;
    }

    public void getNextPage() {

        if(mNext > DEFAULT_PAGE) {
            if(mQuery != null) {
                searchByPage();
            } else {
                loadData();
            }
        }
    }

    public void search(String query) {

        mQuery = query;
//
//        if(mCallFilm != null && mCallFilm.isExecuted())
//            mCallFilm.cancel();
//
//        mSWModelList.clear();
//
//        if(mQuery.length() < 2){
//            mLiveData.setValue(mSWModelList);
//            return;
//        }
//
//        searchByPage();
    }

    public int getSpanCount() {

        if(mId.equals("starships") || mId.equals("vehicles") ) {
            return getApplication().getApplicationContext().getResources().getInteger(R.integer.grid_span_count_wide);
        } else {
            return getApplication().getApplicationContext().getResources().getInteger(R.integer.grid_span_count_tall);
        }
    }

    public int getCount() {
        return mCount;
    }

    public String getId() {
        return mId;
    }

    public SWModel getItem(int position) {
        return mSWModelList.get(position);
    }



    private void setupObserver() {

        ApiBatcher.init();

        mObserver = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Timber.d("onNext(): " +mId + " page " + mNext);
                onLoadResponse((SWModelList<SWModel>) o);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete");
            }
        };
    }


    private void loadData() {

        Timber.d("loadData()");
        Timber.d("mId = " + mId);


        if(mId.equals(getApplication().getResources().getString(R.string.category_id_films))) {
            ApiBatcher.fetchFilmsPage(mNext, mObserver);

        } else if(mId.equals(getApplication().getResources().getString(R.string.category_id_people))) {
            ApiBatcher.fetchPeoplePage(mNext, mObserver);

        } else if(mId.equals(getApplication().getResources().getString(R.string.category_id_species))) {
            ApiBatcher.fetchSpeciesPage(mNext, mObserver);

        } else if(mId.equals(getApplication().getResources().getString(R.string.category_id_starships))) {
            ApiBatcher.fetchStarshipsPage(mNext, mObserver);

        } else if(mId.equals(getApplication().getResources().getString(R.string.category_id_vehicles))) {
            ApiBatcher.fetchVehiclesPage(mNext, mObserver);

        } else if(mId.equals(getApplication().getResources().getString(R.string.category_id_planets))) {
            ApiBatcher.fetchPlanetsPage(mNext, mObserver);
        }

    }

    private void onLoadResponse(SWModelList<SWModel> list) {

        if(list != null) {
            mCount = list.count;

            mNext = getNextPage(list.next);

            if(list.results.get(0) instanceof Film) {
                // sort by episode
                Collections.sort(list.results, new Comparator<SWModel>() {
                    public int compare(SWModel o1, SWModel o2) {
                        return ((Film) o1).episodeId - ((Film) o2).episodeId;
                    }
                });
            }

            mSWModelList.addAll(list.results);
            mLiveData.setValue(mSWModelList);
        }
    }

    private void searchByPage() {

        Timber.d("searchByPage()" );

//        mCallFilm = StarWarsService.getApiInstance().searchFilms(mNext, mQuery);
//        mCallFilm.enqueue(new Callback<SWModelList<Film>>() {
//
//            @Override
//            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
//
//                onSearchResponse(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
//                Timber.e("error: " + t.getMessage());
//                onSearchFailure();
//            }
//        });

    }

    private void onSearchResponse(SWModelList<Film> list) {

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

            mNext = getNextPage(list.next);

            if(list.next != null) {
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

    private void onSearchFailure() {

        mCount = -1; // error state
        resetPageCounter();
        mSWModelList.clear();
        mLiveData.setValue(mSWModelList);
    }

    private int getNextPage(String url) {

        if(url == null)
            return DEFAULT_PAGE;

        Uri uri = Uri.parse(url);
        Set<String> args = uri.getQueryParameterNames();

        if(uri.getQueryParameter("page") != null) {
            return Integer.parseInt(uri.getQueryParameter("page"));
        }

        // default
        return DEFAULT_PAGE;
    }

    private void resetPageCounter() {
        mNext = DEFAULT_PAGE;
    }



}
