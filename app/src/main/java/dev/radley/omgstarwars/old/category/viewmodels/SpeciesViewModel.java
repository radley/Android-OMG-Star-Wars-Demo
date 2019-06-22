package dev.radley.omgstarwars.old.category.viewmodels;

import dev.radley.omgstarwars.data.Species;
import dev.radley.omgstarwars.data.SWModelList;
import dev.radley.omgstarwars.network.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class SpeciesViewModel extends BaseCategoryViewModel {

    private Call<SWModelList<Species>> mCallSpecies;

    public Species getItem(int position) {
        return (Species) mSWModelList.get(position);
    }

    protected void loadByPage() {

        Timber.d("loadSpecies()");

        mCallSpecies = service.getApi().getAllSpecies(mPage);
        mCallSpecies.enqueue(new Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Timber.d("error: " + t.getMessage());
                resetPageCounter();
            }
        });
    }

    protected void onLoadResponse(SWModelList<Species> list) {

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

        if(mCallSpecies != null && mCallSpecies.isExecuted())
            mCallSpecies.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallSpecies = service.getApi().searchSpecies(mPage, mQuery);
        mCallSpecies.enqueue(new retrofit2.Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Timber.e("error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
