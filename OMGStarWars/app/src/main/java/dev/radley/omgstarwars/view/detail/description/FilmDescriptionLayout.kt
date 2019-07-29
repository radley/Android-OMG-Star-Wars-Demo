package dev.radley.omgstarwars.view.detail.description


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.Film
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.view_detail_film.view.*

/**
 * Layout for Film details text
 */
class FilmDescriptionLayout : DescriptionLayout {


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
        
        director.text = model.director
        producer.text = model.producer
        release_date.text = FormatUtils.getFormattedDate(context, model.created)
        
        val strs = model.producer.split(",").toTypedArray()
        producerCount.text = resources.getQuantityString(R.plurals.producers_count, strs.size)
    }
}
