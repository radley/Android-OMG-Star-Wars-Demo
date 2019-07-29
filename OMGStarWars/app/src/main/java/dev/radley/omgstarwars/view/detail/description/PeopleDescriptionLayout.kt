package dev.radley.omgstarwars.view.detail.description


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.People
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.view_detail_people.view.*

/**
 * Layout for People details text
 */
class PeopleDescriptionLayout : DescriptionLayout {


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

        dob.text = model.birthYear
        hair_color.text = model.hairColor
        skin_color.text = model.skinColor
        gender.text = model.gender
        person_height.text = FormatUtils.getFormattedHeightCm(context, model.height)
        mass.text = FormatUtils.getFormattedKg(context, model.mass)
    }
}
