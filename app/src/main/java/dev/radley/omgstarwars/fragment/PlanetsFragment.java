package dev.radley.omgstarwars.fragment;

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
import dev.radley.omgstarwars.adapter.PlanetsAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.Planet;
import dev.radley.omgstarwars.model.viewmodel.category.PlanetsViewModel;

public class PlanetsFragment extends BaseCategoryFragment {

    protected PlanetsAdapter mAdapter;
    protected PlanetsViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        String query = "";

        if (arguments != null && arguments.containsKey(SearchExtras.QUERY))
            query = arguments.getString(SearchExtras.QUERY);

        mViewModel = ViewModelProviders.of(this).get(PlanetsViewModel.class);
        mViewModel.getPlanets(query).observe(this, new Observer<ArrayList<Planet>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Planet> filmList) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new PlanetsAdapter(getActivity(), filmList);
                    mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {

                        @Override
                        public void onBottomReached(int position) {
                            mViewModel.getNextPage();
                        }
                    });

                    mRecyclerView.setAdapter(mAdapter);
                }

                // search activity results count
                if(mSearchCallback != null)
                    mSearchCallback.onResultUpdate(filmList.size());
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {
                startActivity(DetailExtras.getIntent(getActivity(), mViewModel.getCategoryId(position), (Planet) mViewModel.getItem(position)));
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
