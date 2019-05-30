package dev.radley.omgstarwars.fragment;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.adapter.PlanetsAdapter;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.OmgStarWarsApi;
import dev.radley.omgstarwars.activity.PlanetActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

public class PlanetsFragment extends BaseCategoryFragment {


    protected PlanetsAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;
    protected boolean mLoading = false;

    protected ArrayList<Planet> mList = new ArrayList<Planet>();

    @Override
    protected void initGrid() {
        if (mList.size() == 0) {
            getGridItemsByPage(mPage);
            return;
        }

        populateGrid();
    }

    @Override
    protected void populateGrid() {
        mAdapter = new PlanetsAdapter(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                final Intent intent = new Intent(getActivity(), PlanetActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(DetailIntentUtil.RESOURCE, mList.get(position));
                intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("planets", mList.get(position).url));
                intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_planet);

                startActivity(intent);

            }
        });

        mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {

            @Override
            public void onBottomReached(int position) {
                if (mTotalItems > mList.size() && !mLoading) {
                    mPage++;
                    getGridItemsByPage(mPage);
                }
            }
        });

    }

    protected void getGridItemsByPage(int page) {

        mLoading = true;

        Call<SWModelList<Planet>> call = OmgStarWarsApi.getApi().getAllPlanets(page);
        call.enqueue(new retrofit2.Callback<SWModelList<Planet>>() {

            @Override
            public void onResponse(Call<SWModelList<Planet>> call, retrofit2.Response<SWModelList<Planet>> response) {
                onCallbackSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Planet>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<Planet> list) {

        // init new list
        if (mList.size() == 0) {
            mTotalItems = list.count;
            mPageSize = list.results.size();
            mList.addAll(list.results);

            populateGrid();


        } else { // update list

            Log.d(OmgSWUtil.getTag(), "update list");

            int curSize = mAdapter.getItemCount();
            mList.addAll(list.results);
            mAdapter.notifyItemRangeInserted(curSize, list.results.size());
        }

        mLoading = false;
    }



}
