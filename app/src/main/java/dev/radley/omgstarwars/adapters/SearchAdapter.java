package dev.radley.omgstarwars.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.text.CustomTypefaceSpan;
import dev.radley.omgstarwars.viewmodels.SWCard;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<SWModel> modelList;
    private String query;
    private Typeface boldTypeface;

    /**
     *
     * @param context needed to access font resource
     * @param list ArrayList<SWModel>
     */
    public SearchAdapter(Context context, ArrayList<SWModel> list) {

        modelList = list;

        // custom fonts require us to use typeface to display bold text style
        boldTypeface = ResourcesCompat.getFont(context, R.font.nunito_sans_extrabold);
    }

    /**
     * Assign <code>query</code> value to match in displayed text
     *
     * @param query string
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Instantiate ViewHolder with thumbnail and title
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView subtitleText;
        private ImageView thumbnail;
        public ViewHolder(View itemView) {

            super(itemView);
            titleText = itemView.findViewById(R.id.title);
            subtitleText = itemView.findViewById(R.id.subtitle);
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
    public SearchAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.card_result, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    /**
     *
     * @param holder ViewHolder
     * @param position adapter position
     */
    @Override
    public void onBindViewHolder(@NotNull SearchAdapter.ViewHolder holder, int position) {

        SWModel item = modelList.get(position);
        holder.titleText.setText(getBoldResultText(item.getTitle()));

        // Searching in Starship includes Starship.model result
        // so we should display it as subtitle text
        if(item instanceof Starship) {
            holder.subtitleText.setText(getBoldResultText(((Starship) item).model));
        }

        // assign default & missing images
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(SWCard.getPlaceholderImage(item.getCategoryId()))
                .error(SWCard.getFallbackImage(item.getCategoryId()));

        // add image to thumbnail with fade-in
        Glide.with(holder.thumbnail.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(item.getImagePath()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.thumbnail);
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
     * Find <code>query</code> in <code>text</code> string and make it bold
     *
     * @param text text to be displayed
     * @return formatted text
     */
    private SpannableString getBoldResultText(String text) {

        SpannableString spannable = new SpannableString(text);

        if (text.toLowerCase().contains(query.toLowerCase())) {

            spannable.setSpan(new CustomTypefaceSpan("", boldTypeface),
                    text.toLowerCase().indexOf(query.toLowerCase()),
                    text.toLowerCase().indexOf(query.toLowerCase()) + query.length(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannable;
    }
}