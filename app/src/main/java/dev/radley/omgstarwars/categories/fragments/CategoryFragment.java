package dev.radley.omgstarwars.categories.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swapi.models.Film;
import com.swapi.sw.StarWarsApi;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;

public abstract class CategoryFragment extends Fragment {


    protected Context mContext;

    protected GridLayoutManager mLayoutManager;

    protected View mView;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_category, container, false);


        // using SWAPI SDK
        // https://github.com/Oleur/SWAPI-Android-SDK
        StarWarsApi.init();
        getGridItemsByPage(mPage);

        return mView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    // To override
    protected abstract void getGridItemsByPage(int page);

}
