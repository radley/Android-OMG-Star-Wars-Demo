package dev.radley.omgstarwars.view.detailview


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.Species
import dev.radley.omgstarwars.utilities.FormatUtils

/**
 * Layout for Species details text
 */
class SpeciesDetailView : DetailView {

    internal lateinit var model: Species

    constructor(context: Context) : super(context) {}

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model as Species

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_species, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    internal fun populateContent() {

        setText(R.id.classification, model.classification)
        setText(R.id.designation, model.designation)
        setText(R.id.skin_colors, model.skinColors)
        setText(R.id.hair_color, model.hairColors)
        setText(R.id.eye_colors, model.eyeColors)
        setText(R.id.language, model.language)
        setText(R.id.average_height, FormatUtils.getFormattedHeightCm(context, model.averageHeight))
        setText(R.id.average_lifespan, FormatUtils.getFormattedYears(context, model.averageLifespan))
    }
}