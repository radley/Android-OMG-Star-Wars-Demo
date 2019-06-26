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
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.viewmodels.SWCard;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.ViewHolder> {

    private ArrayList<SWModel> modelList;
    private int cardLayout;

    private final OnItemClickListener clickListener;

    /**
     *
     * @param list ArrayList<SWModel>
     * @param listener OnItemClickListener
     */
    public RelatedAdapter(ArrayList<SWModel> list, OnItemClickListener listener) {

        modelList = list;
        clickListener = listener;

        // use wider layout for Starships and Vehicles
        if (list.get(0).getCategoryId().equals("starships") ||
                list.get(0).getCategoryId().equals("vehicles")) {
            cardLayout = R.layout.card_row_wide;
        } else
            cardLayout = R.layout.card_row;
    }


    /**
     * Instantiate ViewHolder with thumbnail and title
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private ImageView thumbnail;
        ViewHolder(View itemView) {

            super(itemView);
            titleText = itemView.findViewById(R.id.title);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }

        void bind(final SWModel item, final OnItemClickListener listener) {

            titleText.setText(item.getTitle());

            // assign default & missing images
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(SWCard.getPlaceholderImage(item.getCategoryId()))
                    .error(SWCard.getFallbackImage(item.getCategoryId()));

            // add image to thumbnail with fade-in
            Glide.with(thumbnail.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(item.getImagePath()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(thumbnail);

            thumbnail.setOnClickListener(v -> listener.onItemClick(item));
        }
    }


    /**
     * Bind clickListener with view holder
     *
     * @param holder ViewHolder
     * @param position adapter position
     */
    @Override
    public void onBindViewHolder(@NotNull RelatedAdapter.ViewHolder holder, int position) {

        holder.bind(modelList.get(position), clickListener);
    }


    /**
     *
     * @return <code>modelList.size()</code>
     */
    @Override
    public int getItemCount() {
        return modelList.size();
    }


    /**
     * Create ViewHolder
     *
     * @param parent ViewGroup
     * @param viewType not used
     * @return new ViewHolder
     */
    @NotNull
    @Override
    public RelatedAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(cardLayout, parent, false);
        return new ViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(SWModel item);
    }
}