package dev.radley.omgstarwars.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.model.sw.Planet;

public class PlanetsAdapter extends RecyclerView.Adapter<PlanetsAdapter.ViewHolder> {


    private ArrayList<Planet> mPlanets;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    OnBottomReachedListener onBottomReachedListener;

    public PlanetsAdapter(Context context, ArrayList<Planet> people) {
        mPlanets = people;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private ImageView thumbnail;
        public ViewHolder(View itemView) {

            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public PlanetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.card_grid, parent, false);
        return new PlanetsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PlanetsAdapter.ViewHolder holder, int position) {

        Planet item = mPlanets.get(position);
        holder.titleText.setText(item.name);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_square)
                .error(R.drawable.generic_planet);

        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Util.getAssetImage("planets", item.url)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.thumbnail);

        if (position == mPlanets.size() - 1){

            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return mPlanets.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }


}