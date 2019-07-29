package dev.radley.omgstarwars.view.detail.description


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.Species
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.view_detail_species.view.*

/**
 * Layout for Species details text
 */
class SpeciesDescriptionLayout : DescriptionLayout {

    internal lateinit var model: Species

    constructor(context: Context) : super(context)

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
    private fun populateContent() {

        classification.text = model.classification
        designation.text = model.designation
        skin_colors.text = model.skinColors
        hair_color.text = model.hairColors
        eye_colors.text = model.eyeColors
        language.text = model.language
        average_height.text = FormatUtils.getFormattedHeightCm(context, model.averageHeight)
        average_lifespan.text = FormatUtils.getFormattedYears(context, model.averageLifespan)
    }
}
