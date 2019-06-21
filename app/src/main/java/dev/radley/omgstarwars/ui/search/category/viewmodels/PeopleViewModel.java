package dev.radley.omgstarwars.ui.search.category.viewmodels;

import dev.radley.omgstarwars.network.model.People;
import dev.radley.omgstarwars.network.model.SWModelList;
import dev.radley.omgstarwars.network.api.StarWarsService;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class PeopleViewModel extends BaseCategoryViewModel {

    private Call<SWModelList<People>> mCallPeople;

    public People getItem(int position) {
        return (People) mSWModelList.get(position);
    }

    protected void loadByPage() {

        mCallPeople = StarWarsService.getApiInstance().getAllPeople(mPage);
        mCallPeople.enqueue(new Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {
                onLoadResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Timber.e("error: " + t.getMessage());
                resetPageCounter();
            }
        });
    }

    protected void onLoadResponse(SWModelList<People> list) {

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

        if(mCallPeople != null && mCallPeople.isExecuted())
            mCallPeople.cancel();

        mSWModelList.clear();

        if(mQuery.length() < 2){
            mLiveData.setValue(mSWModelList);
            return;
        }

        searchByPage();
    }

    protected void searchByPage() {
        mCallPeople = StarWarsService.getApiInstance().searchPeople(mPage, mQuery);
        mCallPeople.enqueue(new retrofit2.Callback<SWModelList<People>>() {

            @Override
            public void onResponse(Call<SWModelList<People>> call, retrofit2.Response<SWModelList<People>> response) {

                onSearchResponse(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<People>> call, Throwable t) {
                Timber.e("error: " + t.getMessage());
                onSearchFailure();
            }
        });
    }
}
