package dev.radley.omgstarwars.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;

public abstract class BaseCategoryFragment extends Fragment {

    protected View mView;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected OnHeadlineSelectedListener mSearchCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount()));

        return mView;
    }

    // called from activity to scroll back to top
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
    public abstract void getResultsFor(String query);


    protected int getSpanCount() {
        return getResources().getInteger(R.integer.grid_span_count_tall);
    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.mSearchCallback = callback;
    }

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    public interface OnHeadlineSelectedListener {
        public void onResultUpdate(int position);
    }

}
