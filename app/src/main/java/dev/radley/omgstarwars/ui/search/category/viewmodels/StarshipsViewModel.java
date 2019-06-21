package dev.radley.omgstarwars.ui.search.category.viewmodels;

import dev.radley.omgstarwars.network.model.SWModelList;
import dev.radley.omgstarwars.network.model.Starship;
import dev.radley.omgstarwars.network.api.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class StarshipsViewModel extends BaseCategoryViewModel {
    
    private Call<SWModelList<Starship>> mCallStarships;

    public Starship getItem(int position) {
        return (Starship)mSWModelList.get(position);
    }
    
    protected void loadByPage() {

        mCallStarships = StarWarsService.getApiInstance().getAllStarships(mPage);
        mCallStarships.enqueue(new Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {
                Timber.d("error: " + t.getMessage());
                resetPageCounter();
            }
        });
    }

    protected void onLoadResponse(SWModelList<Starship> list) {

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

        if(mCallStarships != null && mCallStarships.isExecuted())
            mCallStarships.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {

        mCallStarships = StarWarsService.getApiInstance().searchStarships(mPage, mQuery);
        mCallStarships.enqueue(new retrofit2.Callback<SWModelList<Starship>>() {

            @Override
            public void onResponse(Call<SWModelList<Starship>> call, retrofit2.Response<SWModelList<Starship>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Starship>> call, Throwable t) {

                Timber.e("error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
