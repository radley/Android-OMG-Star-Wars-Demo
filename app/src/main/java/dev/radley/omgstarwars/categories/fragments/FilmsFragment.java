package dev.radley.omgstarwars.categories.fragments;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.swapi.models.Film;
import com.swapi.models.SWModelList;
import com.swapi.sw.StarWarsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.categories.adapter.FilmsAdapter;
import dev.radley.omgstarwars.categories.listener.OnItemSelectedListener;
import dev.radley.omgstarwars.detail.FilmActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilmsFragment extends CategoryFragment {


    protected FilmsAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;

    protected ArrayList<Film> mList = new ArrayList<Film>();

    protected void getGridItemsByPage(int page) {

        StarWarsApi.getApi().getAllFilms(page, new Callback<SWModelList<Film>>() {

            @Override
            public void success(SWModelList list, Response response) {
                onCallbackSuccess(list);
            }

            @Override
            public void failure(RetrofitError error) {

                //Something wrong
                Log.d(Util.getTag(), "error: " + error);
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<Film> list) {


        Log.d(Util.getTag(), "mList.size(): " + mList.size());

        if (mList.size() == 0) {
            mTotalItems = list.count;
            mPageSize = list.results.size();
            mList.addAll(list.results);

            // sort by episode
            Collections.sort(mList, new Comparator<Film>() {
                public int compare(Film o1, Film o2) {
                    return o1.episodeId - o2.episodeId;
                }
            });

            mRecyclerView = (RecyclerView) mView.findViewById(R.id.grid);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_span_count)));
            mAdapter = new FilmsAdapter(getContext(), mList);
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.addOnItemTouchListener(new OnItemSelectedListener(getContext()) {

                public void onItemSelected(RecyclerView.ViewHolder holder, int position) {

//                    if (!(holder instanceof PhotoViewHolder)) {
//                        return;
//                    }

                    //GalleryImageBinding binding = ((PhotoViewHolder) holder).getBinding();

//                    final Intent intent = getDetailActivityStartIntent(activity, mPhotoList, position,
//                            mSharedElementTransition, mDetailViewEnterTransition, mDetailViewExitTransition);
//                    final ActivityOptionsCompat activityOptions = getActivityOptions(binding);
//
//                    activity.startActivityForResult(intent, IntentUtil.REQUEST_CODE,
//                            activityOptions.toBundle());

                    Log.d(Util.getTag(), "position: " + position);
                    Log.d(Util.getTag(), "episodeId: " + mList.get(position).episodeId);
                    Log.d(Util.getTag(), "title: " + mList.get(position).title);
                    Log.d(Util.getTag(), "path: " + "file:///android_asset/films/" + ((Film) mList.get(position)).episodeId +".jpg");


                    final Intent intent = new Intent(getActivity(), FilmActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailIntentUtil.RESOURCE, mList.get(position));
                    intent.putExtra(DetailIntentUtil.IMAGE_URL, "file:///android_asset/films/" + ((Film) mList.get(position)).episodeId +".jpg");

                    startActivity(intent);

                }
            });

        }


        Log.d(Util.getTag(), "mList: " + mList);

    }
}
