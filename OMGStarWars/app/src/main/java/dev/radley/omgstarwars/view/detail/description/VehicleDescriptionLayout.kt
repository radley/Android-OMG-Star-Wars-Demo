package dev.radley.omgstarwars.view.detail.description


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.models.Vehicle
import dev.radley.omgstarwars.utilities.FormatUtils
import kotlinx.android.synthetic.main.view_detail_vehicle.view.*

/**
 * Layout for Vehicle details text
 */
class VehicleDescriptionLayout : DescriptionLayout {

    internal lateinit var model: Vehicle

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {
        this.model = model as Vehicle

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_vehicle, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        vehicle_model.text = model.model
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
