package dev.radley.omgstarwars.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.adapter.StarshipsAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.Starship;
import dev.radley.omgstarwars.model.viewmodel.category.StarshipsViewModel;

public class StarshipsFragment extends BaseCategoryFragment {

    protected StarshipsAdapter mAdapter;
    protected StarshipsViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        String query = "";

        if (arguments != null && arguments.containsKey(SearchExtras.QUERY))
            query = arguments.getString(SearchExtras.QUERY);

        mViewModel = ViewModelProviders.of(this).get(StarshipsViewModel.class);
        mViewModel.getList(query).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new StarshipsAdapter(getActivity(), list);
                    mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {

                        @Override
                        public void onBottomReached(int position) {

                            Log.d(Util.tag, "setOnBottomReachedListener: " + position);
                            mViewModel.getNextPage();
                        }
                    });

                    mRecyclerView.setAdapter(mAdapter);
                }

                // search activity results count
                if(mSearchCallback != null)
                    mSearchCallback.onResultUpdate(mViewModel.getCount());
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {
                startActivity(DetailExtras.getIntent(getActivity(), mViewModel.getCategoryId(position), (Starship) mViewModel.getItem(position)));
            }
        });

        return mView;
    }

    @Override
    protected int getSpanCount() {
        return getResources().getInteger(R.integer.grid_span_count_wide);
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
    }

    @Override
    public void getResultsFor(String query) {
        mViewModel.search(query);
    }
}
