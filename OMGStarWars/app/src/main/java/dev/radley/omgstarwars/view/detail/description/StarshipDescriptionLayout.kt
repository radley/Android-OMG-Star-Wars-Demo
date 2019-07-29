package dev.radley.omgstarwars.view.detail.description


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.Starship
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.view_detail_starship.view.*

/**
 * Layout for Starship details text
 */
class StarshipDescriptionLayout : DescriptionLayout {

    internal lateinit var model: Starship

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model as Starship

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_starship, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        hyperdrive_rating.text = model.hyperdriveRating
        mglt.text = model.mglt
        starship_class.text = model.starshipClass

        ship_model.text = model.model
        manufacturer.text = model.manufacturer
        length.text = FormatUtils.getFormattedLengthM(context, model.length)
        cargo_capacity.text = FormatUtils.getFormattedTonnage(context, model.cargoCapacity)
        cost_in_credits.text = FormatUtils.getFormattedCredits(context, model.costInCredits)
        max_atmosphering_speed.text = FormatUtils.getFormattedSpeedKph(context, model.maxAtmospheringSpeed)
        crew.text = FormatUtils.getFormattedNumber(model.crew)
        passengers.text = FormatUtils.getFormattedNumber(model.passengers)
        consumables.text = FormatUtils.getFormattedNumber(model.consumables)
    }
}

