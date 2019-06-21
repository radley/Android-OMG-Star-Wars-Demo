package dev.radley.omgstarwars.ui.detail;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.listener.OnBottomReachedListener;
import dev.radley.omgstarwars.network.model.SWModel;
import dev.radley.omgstarwars.ui.model.UIModel;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.ViewHolder> {

    private ArrayList<SWModel> mSWModelList;
    private OnBottomReachedListener onBottomReachedListener;
    private int mCardLayout;

    private final OnItemClickListener mListener;

    public RelatedAdapter(ArrayList<SWModel> list, OnItemClickListener listener) {

        mSWModelList = list;
        mListener = listener;

        // custom layout
        if (list.get(0).getCategoryId().equals("starships") ||
                list.get(0).getCategoryId().equals("vehicles")) {
            mCardLayout = R.layout.card_row_wide;
        } else
            mCardLayout = R.layout.card_row;

    }

    public interface OnItemClickListener {
        void onItemClick(SWModel item);
    }

    @Override
    public void onBindViewHolder(@NotNull RelatedAdapter.ViewHolder holder, int position) {

        holder.bind(mSWModelList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mSWModelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private ImageView thumbnail;
        public ViewHolder(View itemView) {

            super(itemView);
            titleText = itemView.findViewById(R.id.title);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }

        public void bind(final SWModel item, final OnItemClickListener listener) {

            titleText.setText(item.getTitle());

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(UIModel.getPlaceholderImage(item.getCategoryId()))
                    .error(UIModel.getFallbackImage(item.getCategoryId()));

            Glide.with(thumbnail.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(item.getImagePath()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    listener.onItemClick(item);
                }
            });
        }
    }

    @NotNull
    @Override
    public RelatedAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(mCardLayout, parent, false);
        return new ViewHolder(v);
    }


}