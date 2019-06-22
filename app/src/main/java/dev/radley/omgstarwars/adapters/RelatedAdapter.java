package dev.radley.omgstarwars.adapters;

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
import dev.radley.omgstarwars.listeners.OnBottomReachedListener;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.utilities.SWCard;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.ViewHolder> {

    private ArrayList<SWModel> modelList;
    private OnBottomReachedListener onBottomReachedListener;
    private int cardLayout;

    private final OnItemClickListener clickListener;

    public RelatedAdapter(ArrayList<SWModel> list, OnItemClickListener listener) {

        modelList = list;
        clickListener = listener;

        // custom layout
        if (list.get(0).getCategoryId().equals("starships") ||
                list.get(0).getCategoryId().equals("vehicles")) {
            cardLayout = R.layout.card_row_wide;
        } else
            cardLayout = R.layout.card_row;

    }

    public interface OnItemClickListener {
        void onItemClick(SWModel item);
    }

    @Override
    public void onBindViewHolder(@NotNull RelatedAdapter.ViewHolder holder, int position) {

        holder.bind(modelList.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
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
                    .placeholder(SWCard.getPlaceholderImage(item.getCategoryId()))
                    .error(SWCard.getFallbackImage(item.getCategoryId()));

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

        View v = LayoutInflater.from(parent.getContext()).inflate(cardLayout, parent, false);
        return new ViewHolder(v);
    }


}