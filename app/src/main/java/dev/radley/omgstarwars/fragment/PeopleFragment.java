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

import dev.radley.omgstarwars.adapter.PeopleAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.People;
import dev.radley.omgstarwars.model.viewmodel.category.PeopleViewModel;

public class PeopleFragment extends BaseCategoryFragment {

    protected PeopleAdapter mAdapter;
    protected PeopleViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        String query = "";

        if (arguments != null && arguments.containsKey(SearchExtras.QUERY))
            query = arguments.getString(SearchExtras.QUERY);

        mViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);
        mViewModel.getPeople(query).observe(this, new Observer<ArrayList<People>>() {

            @Override
            public void onChanged(@Nullable ArrayList<People> filmList) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new PeopleAdapter(getActivity(), filmList);
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
                startActivity(DetailExtras.getIntent(getActivity(), mViewModel.getCategoryId(position), (People) mViewModel.getItem(position)));
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
