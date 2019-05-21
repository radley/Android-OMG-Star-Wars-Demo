package dev.radley.omgstarwars.categories.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.swapi.models.People;
import com.swapi.models.SWModelList;
import com.swapi.sw.StarWarsApi;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.DetailIntentUtil;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.categories.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.categories.listener.OnItemSelectedListener;
import dev.radley.omgstarwars.categories.adapter.PeopleAdapter;
import dev.radley.omgstarwars.detail.PeopleActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PeopleFragment extends CategoryFragment {


    protected PeopleAdapter mAdapter;

    protected int mTotalItems;
    protected int mPage = 1;
    protected int mPageSize;
    protected boolean mLoading = false;

    protected ArrayList<People> mList = new ArrayList<People>();

    protected void getGridItemsByPage(int page) {

        mLoading = true;

        StarWarsApi.getApi().getAllPeople(page, new Callback<SWModelList<People>>() {

            @Override
            public void success(SWModelList list, Response response) {
                onCallbackSuccess(list);
            }

            @Override
            public void failure(RetrofitError error) {

                mLoading = false;
                //Something wrong
                Log.d(Util.getTag(), "error: " + error);
            }
        });
    }

    protected void onCallbackSuccess(SWModelList<People> list) {


        Log.d(Util.getTag(), "mList.size(): " + mList.size());

        // init new list
        if (mList.size() == 0) {
            mTotalItems = list.count;
            mPageSize = list.results.size();
            mList.addAll(list.results);

            mRecyclerView = (RecyclerView) mView.findViewById(R.id.grid);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grid_span_count)));
            mAdapter = new PeopleAdapter(getContext(), mList);
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


                    final Intent intent = new Intent(getActivity(), PeopleActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.putExtra(DetailIntentUtil.RESOURCE, mList.get(position));
                    intent.putExtra(DetailIntentUtil.IMAGE_URL, "file:///android_asset/people/" + (position+1) +".jpg");

                    startActivity(intent);

                }
            });

            mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {

                @Override
                public void onBottomReached(int position) {
                    if (mTotalItems > mList.size() && !mLoading) {
                        mPage++;
                        getGridItemsByPage(mPage);
                    }
                }
            });

        } else { // update list

            // record this value before making any changes to the existing list
            int curSize = mAdapter.getItemCount();

            // update the existing list
            mList.addAll(list.results);

            // curSize should represent the first element that got added
            // newItems.size() represents the itemCount
            mAdapter.notifyItemRangeInserted(curSize, list.results.size());
        }

        mLoading = false;
    }



}
