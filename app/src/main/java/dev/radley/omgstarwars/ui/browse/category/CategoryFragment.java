package dev.radley.omgstarwars.ui.browse.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.ui.detail.DetailActivity;

public class CategoryFragment extends Fragment {

    private Button mRetryButton;
    private CategoryAdapter mAdapter;
    private CategoryViewModel mViewModel;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RecyclerTouchListener mRecyclerTouchListener;

    private ArrayList<SWModel> mSWModelList = new ArrayList<>();


    public static CategoryFragment newInstance(String category) {
        CategoryFragment myFragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString(SearchExtras.CATEGORY, category);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        assert getArguments() != null;
        String category = getArguments().getString(SearchExtras.CATEGORY);

        View mView = inflater.inflate(R.layout.fragment_category, container, false);

        mRetryButton = mView.findViewById(R.id.retry_button);
        mProgressBar = mView.findViewById(R.id.progress);

        mRecyclerView = mView.findViewById(R.id.grid);

        assert category != null;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount(category)));

        mViewModel = ViewModelProviders.of(this,
                new CategoryInstanceFactory(Objects.requireNonNull(getActivity()).getApplication(), category))
                .get(CategoryViewModel.class);

        observeViewModel(category);
        return mView;
    }

    private void observeViewModel(String category) {
        mViewModel.getList(category).observe(this, swModel -> {

            if(swModel != null) {

                mSWModelList.addAll(swModel);

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new CategoryAdapter(getActivity(), mSWModelList);
                    mAdapter.setOnBottomReachedListener(position -> mViewModel.getNextPage());

                    mRecyclerView.setAdapter(mAdapter);
                }

                mRetryButton.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);


                //list.setVisibility(View.VISIBLE);
                //adapter.notifyDataSetChanged();
            } else {
                //list.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        mRecyclerTouchListener = new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                startActivity(DetailExtras.getIntent(Objects.requireNonNull(getActivity()),
                        mViewModel.getItem(position)));
            }
        };

        mRecyclerView.addOnItemTouchListener(mRecyclerTouchListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        mAdapter = null;
        mRecyclerView.removeOnItemTouchListener(mRecyclerTouchListener);
    }

    private int getSpanCount(String id) {

        if(id.equals(getResources().getString(R.string.category_id_starships)) ||
                id.equals(getResources().getString(R.string.category_id_vehicles)) ) {
            return getResources().getInteger(R.integer.grid_span_count_wide);
        } else {
            return getResources().getInteger(R.integer.grid_span_count_tall);
        }
    }

    // called from activity to scroll back to top
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}
