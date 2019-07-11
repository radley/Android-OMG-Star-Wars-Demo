package dev.radley.omgstarwars.view.detailview


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils

/**
 * Layout for People details text
 */
class PeopleDetailView : DetailView {


    internal lateinit var model: People

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model as People

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_people, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        setText(R.id.dob, model.birthYear)
        setText(R.id.hair_color, model.hairColor)
        setText(R.id.skin_color, model.skinColor)
        setText(R.id.gender, model.gender)
        setText(R.id.height, FormatUtils.getFormattedHeightCm(context, model.height))
        setText(R.id.mass, FormatUtils.getFormattedKg(context, model.mass))
    }
}
