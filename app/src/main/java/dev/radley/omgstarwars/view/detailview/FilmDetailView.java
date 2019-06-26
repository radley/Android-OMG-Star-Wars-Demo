package dev.radley.omgstarwars.view.detailview;


import android.content.Context;
import android.view.LayoutInflater;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.Film;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.utilities.FormatUtils;

/**
 * Layout for Film details text
 */
public class FilmDetailView extends DetailView {


    Film model;

    public FilmDetailView(Context context) {
        super(context);
    }

    public FilmDetailView(Context context, SWModel model) {
        super(context);

        this.context = context;
        this.model = (Film) model;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_detail_film, this, true);

        populateContent();
    }

    /**
     * Add content to layout
     */
    void populateContent() {

        setText(R.id.director, model.director);
        setText(R.id.producer, model.producer);
        setText(R.id.opening_crawl, model.openingCrawl);
        setText(R.id.release_date, FormatUtils.getFormattedDate(context, model.created));
    }
}
