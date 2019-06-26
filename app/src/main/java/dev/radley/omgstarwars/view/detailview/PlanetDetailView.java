package dev.radley.omgstarwars.view.detailview;


import android.content.Context;
import android.view.LayoutInflater;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.Planet;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.utilities.FormatUtils;

public class PlanetDetailView extends DetailView {
    
    Planet model;
    
    public PlanetDetailView(Context context) {
        super(context);
    }

    public PlanetDetailView(Context context, SWModel model) {
        super(context);

        this.context = context;
        this.model = (Planet) model;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_detail_planet, this, true);

        populateContent();
    }

    void populateContent() {

        setText(R.id.climate, model.climate);
        setText(R.id.gravity, model.gravity);
        setText(R.id.terrain, model.terrain);
        setText(R.id.population, FormatUtils.getFormattedNumber(model.population));
        setText(R.id.rotation_period, FormatUtils.getFormattedDays(context, model.rotationPeriod));
        setText(R.id.orbital_period, FormatUtils.getFormattedDays(context, model.orbitalPeriod));
        setText(R.id.diameter, FormatUtils.getFormattedDistance(context, model.diameter));
        setText(R.id.surface_water, FormatUtils.getFormattedPercentage(context, model.surfaceWater));
    }
}
