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
import dev.radley.omgstarwars.model.sw.SWModel;
import dev.radley.omgstarwars.model.sw.Species;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.ViewHolder> {


    private ArrayList<SWModel> mSpecies;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    OnBottomReachedListener onBottomReachedListener;

    public SpeciesAdapter(Context context, ArrayList<SWModel> species) {
        mSpecies = species;
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
    public SpeciesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.card_grid, parent, false);
        return new SpeciesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SpeciesAdapter.ViewHolder holder, int position) {

        SWModel item = mSpecies.get(position);
        holder.titleText.setText(item.name);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .error(R.drawable.generic_species);

        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Util.getAssetImage("species", item.url)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.thumbnail);

        if (position == mSpecies.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return mSpecies.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }


}