package dev.radley.omgstarwars.ui.search.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.bundle.DetailExtras;
import dev.radley.omgstarwars.bundle.SearchExtras;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.network.model.SWModel;
import timber.log.Timber;

public class CategoryFragment extends Fragment {

    protected CategoryAdapter mAdapter;
    protected CategoryViewModel mViewModel;

    protected View mView;
    protected RecyclerView mRecyclerView;
    protected OnHeadlineSelectedListener mSearchCallback;


    public static CategoryFragment newInstance(String category) {
        CategoryFragment myFragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString(SearchExtras.CATEGORY, category);
        myFragment.setArguments(args);
        return myFragment;
    }

    public static CategoryFragment newInstance(String category, String query) {
        CategoryFragment myFragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString(SearchExtras.CATEGORY, category);
        args.putString(SearchExtras.QUERY, query);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        Bundle arguments = getArguments();
        String category = arguments.getString(SearchExtras.CATEGORY);

        String query = "";
        if (arguments != null && arguments.containsKey(SearchExtras.QUERY))
            query = arguments.getString(SearchExtras.QUERY);

        mView = inflater.inflate(R.layout.fragment_category, container, false);

        mViewModel = ViewModelProviders.of(this,
                new CategoryViewFactory(getActivity().getApplication(), category))
                .get(CategoryViewModel.class);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mViewModel.getSpanCount()));

        mViewModel.getList(query).observe(this, new Observer<ArrayList<SWModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SWModel> list) {

                if(mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter = new CategoryAdapter(getActivity(), list);
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
                startActivity(DetailExtras.getIntent(getActivity(), (SWModel) mViewModel.getItem(position)));
            }
        });

        return mView;
    }

    // called from activity to scroll back to top
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.mSearchCallback = callback;
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
    }

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    public interface OnHeadlineSelectedListener {
        public void onResultUpdate(int position);
    }

    public void getResultsFor(String query) {
        mViewModel.search(query);
    }
}
