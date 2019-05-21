package dev.radley.omgstarwars.categories.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.swapi.models.People;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.categories.listener.OnBottomReachedListener;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {


    private ArrayList<People> mPeople;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    OnBottomReachedListener onBottomReachedListener;

    public PeopleAdapter(Context context, ArrayList<People> people) {
        mPeople = people;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private ImageView thumbnail;
        public ViewHolder(View itemView) {

            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.grid_title);
            thumbnail = (ImageView) itemView.findViewById(R.id.grid_thumbnail);
        }
    }

    public void add(int position, People person) {
        mPeople.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeople.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_grid, parent, false);
        return new PeopleAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PeopleAdapter.ViewHolder holder, int position) {

        People item = mPeople.get(position);
        holder.titleText.setText(item.name);

        RequestOptions requestOptions = new RequestOptions()
                .fallback(R.drawable.tall_fallback)
                .placeholder(R.drawable.tall_placeholder);

        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse("file:///android_asset/people/" + (position+1) +".jpg"))
                .into(holder.thumbnail);

        if (position == mPeople.size() - 1){

            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }


}