package dev.radley.omgstarwars.view.detailview


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils

/**
 * Layout for Film details text
 */
class FilmDetailView : DetailView {


    internal lateinit var model: Film

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model as Film

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_film, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        setText(R.id.director, model.director)
        setText(R.id.producer, model.producer)
        setText(R.id.opening_crawl, model.openingCrawl)
        setText(R.id.release_date, FormatUtils.getFormattedDate(context, model.created))
    }
}
