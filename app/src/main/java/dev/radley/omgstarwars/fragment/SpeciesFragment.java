package dev.radley.omgstarwars.fragment;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.adapter.SpeciesAdapter;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.network.OmgStarWarsApi;
import dev.radley.omgstarwars.activity.SpeciesActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

public class SpeciesFragment extends BaseCategoryFragment {


    protected SpeciesAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;
    protected boolean mLoading = false;

    protected ArrayList<Species> mList = new ArrayList<Species>();

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
        mAdapter = new SpeciesAdapter(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                final Intent intent = new Intent(getActivity(), SpeciesActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(DetailIntentUtil.RESOURCE, mList.get(position));
                intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("species", mList.get(position).url));
                intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.placeholder_species);

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

        Call<SWModelList<Species>> call = OmgStarWarsApi.getApi().getAllSpecies(page);
        call.enqueue(new retrofit2.Callback<SWModelList<Species>>() {

            @Override
            public void onResponse(Call<SWModelList<Species>> call, retrofit2.Response<SWModelList<Species>> response) {
                onCallbackSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Species>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<Species> list) {

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
