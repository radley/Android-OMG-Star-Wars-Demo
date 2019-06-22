package dev.radley.omgstarwars.old.category.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.old.category.adapters.PlanetsAdapter;
import dev.radley.omgstarwars.old.category.viewmodels.PlanetsViewModel;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listeners.OnBottomReachedListener;
import dev.radley.omgstarwars.listeners.RecyclerTouchListener;
import dev.radley.omgstarwars.data.Planet;
import dev.radley.omgstarwars.data.SWModel;
import timber.log.Timber;

public class PlanetsFragment extends BaseCategoryFragment {

    protected PlanetsAdapter mAdapter;
    protected PlanetsViewModel mViewModel;

    public static PlanetsFragment newInstance() {
        PlanetsFragment myFragment = new PlanetsFragment();
        return myFragment;
    }

    public static PlanetsFragment newInstance(String query) {
        PlanetsFragment myFragment = new PlanetsFragment();

        Bundle args = new Bundle();
        args.putString(SearchExtras.QUERY, query);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        String query = "";

        if (arguments != null && arguments.containsKey(SearchExtras.QUERY))
            query = arguments.getString(SearchExtras.QUERY);

        mViewModel = ViewModelProviders.of(this).get(PlanetsViewModel.class);
        mViewModel.getList(query).observe(this, new Observer<ArrayList<SWModel>>() {

            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new PlanetsAdapter(getActivity(), list);
                    mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {

                        @Override
                        public void onBottomReached(int position) {

                            Timber.d("setOnBottomReachedListener: " + position);
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
                startActivity(DetailExtras.getIntent(getActivity(), (Planet) mViewModel.getItem(position)));
            }
        });

        return mView;
    }

    @Override
    protected int getSpanCount() {
        return getResources().getInteger(R.integer.grid_span_count_tall);
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
