package dev.radley.omgstarwars.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.adapter.FilmsAdapter;
import dev.radley.omgstarwars.bundle.DetailIntentUtil;
import dev.radley.omgstarwars.bundle.SearchIntentUtil;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.StarWarsApi;
import retrofit2.Call;
import retrofit2.Callback;

public class FilmsFragment extends BaseCategoryFragment {


    protected FilmsAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;

    protected ArrayList<Film> mList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(SearchIntentUtil.RESULT_LIST))
        {
            mList = (ArrayList<Film>) arguments.getSerializable(SearchIntentUtil.RESULT_LIST);

        } else {
            mList = new ArrayList<Film>();
        }


        StarWarsApi.init();
        initGrid();

        return mView;
    }

    public void updateList(ArrayList<Object> list) {

        mList = new ArrayList<Film>();
        for (Object object : list) {
            mList.add(((Film) object));
        }
        mAdapter.notifyDataSetChanged();
    }

    public void clear(){
        mList.clear();
        if(mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initGrid() {
        if (mList.size() == 0) {

            getGridItemsByPage(mPage);
            return;
        }

        populateGrid();
    }

    @Override
    protected void populateGrid() {
        mAdapter = new FilmsAdapter(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                startActivity(DetailIntentUtil.getIntent(getActivity(), mList.get(position).getCategoryId(), (SWModel) mList.get(position)));
            }
        });
    }

    protected void getGridItemsByPage(int page) {

        Call<SWModelList<Film>> call = StarWarsApi.getApi().getAllFilms(page);
        call.enqueue(new Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
                onCallbackSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(OmgSWUtil.tag, "error: " + t.getMessage());
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<Film> list) {

        if (mList.size() == 0) {
            mTotalItems = list.count;
            mPageSize = list.results.size();

            for (Object object : list.results) {
                mList.add(((Film) object));
            }

            // sort by episode
            Collections.sort(mList, new Comparator<Film>() {
                public int compare(Film o1, Film o2) {
                    return o1.episodeId - o2.episodeId;
                }
            });

            populateGrid();

        } else {
            Log.d(OmgSWUtil.tag, "skip");
        }
    }

    protected void onFirstCallback() {

    }
}
