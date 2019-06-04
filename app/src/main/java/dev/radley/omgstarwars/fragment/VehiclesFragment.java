package dev.radley.omgstarwars.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.adapter.VehiclesAdapter;
import dev.radley.omgstarwars.bundle.DetailIntentUtil;
import dev.radley.omgstarwars.bundle.SearchIntentUtil;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.model.sw.Vehicle;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;

public class VehiclesFragment extends BaseCategoryFragment {


    protected VehiclesAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;
    protected boolean mLoading = false;

    protected ArrayList<Vehicle> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(SearchIntentUtil.RESULT_LIST)) {
            mList = (ArrayList<Vehicle>) arguments.getSerializable(SearchIntentUtil.RESULT_LIST);
        } else {
            mList = new ArrayList<Vehicle>();
        }

        StarWarsApi.init();
        initGrid();

        return mView;
    }

    public void updateList(ArrayList<Object> list) {

        mList.clear();
        for (Object object : list) {
            mList.add(((Vehicle) object));
        }
        mAdapter.notifyDataSetChanged();
    }

    public void clear(){
        mList.clear();
        if(mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initGrid() {
        if (mList.size() == 0) {
            getGridItemsByPage(mPage);
            return;
        }

        populateGrid();
    }

    @Override
    protected int getSpanCount() {
        return getResources().getInteger(R.integer.grid_span_count_wide);
    }

    @Override
    protected void populateGrid() {
        mAdapter = new VehiclesAdapter(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                startActivity(DetailIntentUtil.getIntent(getActivity(), mList.get(position).getCategoryId(), (SWModel) mList.get(position)));
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

        Call<SWModelList<Vehicle>> call = StarWarsApi.getApi().getAllVehicles(page);
        call.enqueue(new retrofit2.Callback<SWModelList<Vehicle>>() {

            @Override
            public void onResponse(Call<SWModelList<Vehicle>> call, retrofit2.Response<SWModelList<Vehicle>> response) {
                onCallbackSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Vehicle>> call, Throwable t) {
                Log.d(OmgSWUtil.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<Vehicle> list) {

        // init new list
        if (mList.size() == 0) {
            mTotalItems = list.count;
            mPageSize = list.results.size();
            mList.addAll(list.results);

            if(mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            } else {
                populateGrid();
            }

        } else { // append list

            int curSize = mAdapter.getItemCount();
            mList.addAll(list.results);
            mAdapter.notifyItemRangeInserted(curSize, list.results.size());
        }

        mLoading = false;
    }



}
