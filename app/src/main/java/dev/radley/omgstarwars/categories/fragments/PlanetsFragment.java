package dev.radley.omgstarwars.categories.fragments;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.swapi.models.Planet;
import com.swapi.models.SWModelList;
import com.swapi.sw.StarWarsApi;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.categories.adapter.PlanetsAdapter;
import dev.radley.omgstarwars.categories.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.categories.listener.OnItemSelectedListener;
import dev.radley.omgstarwars.data.OmgStarWarsApi;
import dev.radley.omgstarwars.detail.PlanetActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        mRecyclerView.addOnItemTouchListener(new OnItemSelectedListener(getContext()) {

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

        OmgStarWarsApi.getApi().getAllPlanets(page, new Callback<SWModelList<Planet>>() {

            @Override
            public void success(SWModelList list, Response response) {
                onCallbackSuccess(list);
            }

            @Override
            public void failure(RetrofitError error) {

                mLoading = false;
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
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
