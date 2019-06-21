package dev.radley.omgstarwars.ui.search.category.viewmodels;

import dev.radley.omgstarwars.network.model.Planet;
import dev.radley.omgstarwars.network.model.SWModelList;
import dev.radley.omgstarwars.network.api.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class PlanetsViewModel extends BaseCategoryViewModel {
    
    private Call<SWModelList<Planet>> mCallPlanets;

    public Planet getItem(int position) {
        return (Planet) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallPlanets = StarWarsService.getApiInstance().getAllPlanets(mPage);
        mCallPlanets.enqueue(new Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Timber.d("error: " + t.getMessage());
                resetPageCounter();
            }
        });
    }

    protected void onLoadResponse(SWModelList<Planet> list) {

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

        if(mCallPlanets != null && mCallPlanets.isExecuted())
            mCallPlanets.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallPlanets = StarWarsService.getApiInstance().searchPlanets(mPage, mQuery);
        mCallPlanets.enqueue(new retrofit2.Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Timber.e("error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
