package dev.radley.omgstarwars.adapter;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.model.sw.Film;
import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.Util.Util;
import dev.radley.omgstarwars.model.sw.SWModel;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> {


    private ArrayList<SWModel> mSWModelList;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private OnBottomReachedListener onBottomReachedListener;

    public FilmsAdapter(Context context, ArrayList<SWModel> list) {
        mSWModelList = list;
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
    public FilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.card_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SWModel item = mSWModelList.get(position);
        holder.titleText.setText(item.getTitle());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder_tall)
                .error(R.drawable.placeholder_tall);

        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(Util.getAssetImage("films", item.url)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.thumbnail);

        if (position == mSWModelList.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return mSWModelList.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }

}