package dev.radley.omgstarwars.adapters;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.data.SWModel;
import dev.radley.omgstarwars.listeners.OnBottomReachedListener;
import dev.radley.omgstarwars.utilities.SWCard;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<SWModel> modelList;
    private Context context;
    private OnBottomReachedListener onBottomReachedListener;

    public CategoryAdapter(Context context, ArrayList<SWModel> list) {

        modelList = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private ImageView thumbnail;
        public ViewHolder(View itemView) {

            super(itemView);
            titleText = itemView.findViewById(R.id.title);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }

    @NotNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.card_grid, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull CategoryAdapter.ViewHolder holder, int position) {

        SWModel item = modelList.get(position);
        holder.titleText.setText(item.getTitle());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(SWCard.getPlaceholderImage(item.getCategoryId()))
                .error(SWCard.getFallbackImage(item.getCategoryId()));

        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(item.getImagePath()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.thumbnail);

        if (position == modelList.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }
}