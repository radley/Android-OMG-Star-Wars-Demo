package dev.radley.omgstarwars.categories.fragments;


import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.swapi.models.Film;
import com.swapi.models.SWModelList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.categories.adapter.FilmsAdapter;
import dev.radley.omgstarwars.categories.listener.OnItemSelectedListener;
import dev.radley.omgstarwars.data.OmgStarWarsApi;
import dev.radley.omgstarwars.detail.FilmActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilmsFragment extends BaseCategoryFragment {


    protected FilmsAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;

    protected ArrayList<Film> mList = new ArrayList<Film>();

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

        mRecyclerView.addOnItemTouchListener(new OnItemSelectedListener(getContext()) {

            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

                final Intent intent = new Intent(getActivity(), FilmActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(DetailIntentUtil.RESOURCE, mList.get(position));
                intent.putExtra(DetailIntentUtil.IMAGE_URL, OmgSWUtil.getAssetImage("films", mList.get(position).url));
                intent.putExtra(DetailIntentUtil.PLACEHOLDER_IMAGE, R.drawable.tall_placeholder);

                startActivity(intent);
            }
        });
    }

    protected void getGridItemsByPage(int page) {

        OmgStarWarsApi.getApi().getAllFilms(page, new Callback<SWModelList<Film>>() {

            @Override
            public void success(SWModelList list, Response response) {
                onCallbackSuccess(list);
            }

            @Override
            public void failure(RetrofitError error) {
                //Something wrong
                Log.d(OmgSWUtil.getTag(), "error: " + error);
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
            Log.d(OmgSWUtil.getTag(), "skip");
        }
    }

    protected void onFirstCallback() {

    }
}
