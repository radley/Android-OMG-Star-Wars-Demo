package dev.radley.omgstarwars.categories.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.swapi.models.Film;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> {


    private ArrayList<Film> mFilms;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public FilmsAdapter(Context context, ArrayList<Film> list) {
        mFilms = list;
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

    public void add(int position, Film film) {
        mFilms.add(position, film);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mFilms.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Film item = mFilms.get(position);
        holder.titleText.setText(item.title);



        RequestOptions requestOptions = new RequestOptions()
                .fallback(R.drawable.tall_fallback)
                .placeholder(R.drawable.tall_placeholder);

        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse("file:///android_asset/films/" + mFilms.get(position).episodeId +".jpg"))
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }


}