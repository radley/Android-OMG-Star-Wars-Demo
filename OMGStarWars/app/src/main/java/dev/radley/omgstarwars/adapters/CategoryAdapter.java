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
import dev.radley.omgstarwars.listeners.OnBottomReachedListener;
import dev.radley.omgstarwars.viewmodels.SWImage;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<SWModel> modelList;
    private OnBottomReachedListener onBottomReachedListener;

    /**
     *
     * @param list ArrayList<SWModel>
     */
    public CategoryAdapter(ArrayList<SWModel> list) {

        modelList = list;
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
    }

    /**
     *
     * @param parent ViewGroup
     * @param viewType not used
     * @return new ViewHolder
     */
    @NotNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_grid, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    /**
     * Populate ViewHolder with content, add onBottomReached listener
     *
     * @param holder ViewHolder
     * @param position adapter position
     */
    @Override
    public void onBindViewHolder(@NotNull CategoryAdapter.ViewHolder holder, int position) {

        SWModel item = modelList.get(position);
        holder.titleText.setText(item.getTitle());

        // assign default & missing images
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(SWImage.getPlaceholderImage(item.getCategoryId()))
                .error(SWImage.getFallbackImage(item.getCategoryId()));

        // add image to thumbnail with fade-in
        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(item.getImagePath()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.thumbnail);

        // check for more content when user scrolls to bottom
        if (onBottomReachedListener!=null && position == modelList.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
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
     * Used to check for more content when user scrolls to bottom
     *
     * @param onBottomReachedListener
     */
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }
}