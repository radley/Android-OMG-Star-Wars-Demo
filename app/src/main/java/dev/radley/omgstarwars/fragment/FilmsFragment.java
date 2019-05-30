package dev.radley.omgstarwars.fragment;


import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.OmgSWUtil;
import dev.radley.omgstarwars.adapter.FilmsAdapter;
import dev.radley.omgstarwars.listener.RecyclerTouchListener;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.model.sw.SWModelList;
import dev.radley.omgstarwars.network.OmgStarWarsApi;
import dev.radley.omgstarwars.activity.FilmActivity;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;

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

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext()) {

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

        Call<SWModelList<Film>> call = OmgStarWarsApi.getApi().getAllFilms(page);
        call.enqueue(new Callback<SWModelList<Film>>() {

            @Override
            public void onResponse(Call<SWModelList<Film>> call, retrofit2.Response<SWModelList<Film>> response) {
                onCallbackSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SWModelList<Film>> call, Throwable t) {
                Log.d(OmgSWUtil.getTag(), "error: " + t.getMessage());
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
