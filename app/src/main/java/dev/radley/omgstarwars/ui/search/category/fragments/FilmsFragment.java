package dev.radley.omgstarwars.ui.search.category.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.ui.search.category.adapters.FilmsAdapter;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.network.model.Film;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.ui.search.category.viewmodels.FilmViewModel;
import timber.log.Timber;

public class FilmsFragment extends BaseCategoryFragment {

    protected FilmsAdapter mAdapter;
    protected FilmViewModel mViewModel;

    public static FilmsFragment newInstance() {
        FilmsFragment myFragment = new FilmsFragment();
        return myFragment;
    }

    public static FilmsFragment newInstance(String query) {
        FilmsFragment myFragment = new FilmsFragment();

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

        mViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        mViewModel.getList(query).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new FilmsAdapter(getActivity(), list);
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
                startActivity(DetailExtras.getIntent(getActivity(), (Film) mViewModel.getItem(position)));
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
