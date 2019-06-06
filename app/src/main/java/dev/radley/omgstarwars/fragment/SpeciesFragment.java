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

import dev.radley.omgstarwars.adapter.SpeciesAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.Species;
import dev.radley.omgstarwars.model.viewmodel.category.SpeciesViewModel;

public class SpeciesFragment extends BaseCategoryFragment {

    protected SpeciesAdapter mAdapter;
    protected SpeciesViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        String query = "";

        if (arguments != null && arguments.containsKey(SearchExtras.QUERY))
            query = arguments.getString(SearchExtras.QUERY);

        mViewModel = ViewModelProviders.of(this).get(SpeciesViewModel.class);
        mViewModel.getSpecies(query).observe(this, new Observer<ArrayList<Species>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Species> filmList) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new SpeciesAdapter(getActivity(), filmList);
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
                startActivity(DetailExtras.getIntent(getActivity(), mViewModel.getCategoryId(position), (Species) mViewModel.getItem(position)));
            }
        });

        return mView;
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
