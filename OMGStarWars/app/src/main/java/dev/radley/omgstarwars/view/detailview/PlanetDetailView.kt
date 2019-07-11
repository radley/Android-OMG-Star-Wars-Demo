package dev.radley.omgstarwars.view.detailview


import android.content.Context
import android.view.LayoutInflater

import dev.radley.omgstarwars.R
import dev.radley.omgstarwars.models.Planet
import dev.radley.omgstarwars.models.SWModel
import dev.radley.omgstarwars.utilities.FormatUtils

/**
 * Layout for Planet details text
 */
class PlanetDetailView : DetailView {

    internal lateinit var model: Planet

    constructor(context: Context) : super(context) {}

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model as Planet

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_planet, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    internal fun populateContent() {

        setText(R.id.climate, model.climate)
        setText(R.id.gravity, model.gravity)
        setText(R.id.terrain, model.terrain)
        setText(R.id.population, FormatUtils.getFormattedNumber(model.population))
        setText(R.id.rotation_period, FormatUtils.getFormattedDays(context, model.rotationPeriod))
        setText(R.id.orbital_period, FormatUtils.getFormattedDays(context, model.orbitalPeriod))
        setText(R.id.diameter, FormatUtils.getFormattedDistance(context, model.diameter))
        setText(R.id.surface_water, FormatUtils.getFormattedPercentage(context, model.surfaceWater))
    }
}
