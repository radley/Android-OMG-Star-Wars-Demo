package dev.radley.omgstarwars.view.detailview;


import android.content.Context;
import android.view.LayoutInflater;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Starship;
import dev.radley.omgstarwars.utilities.FormatUtils;

public class StarshipDetailView extends DetailView {

    Starship model;

    public StarshipDetailView(Context context) {
        super(context);
    }

    public StarshipDetailView(Context context, SWModel model) {
        super(context);

        this.context = context;
        this.model = (Starship) model;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_detail_starship, this, true);

        populateContent();
    }

    void populateContent() {

        setText(R.id.hyperdrive_rating, model.hyperdriveRating);
        setText(R.id.mglt, model.mglt);
        setText(R.id.starship_class, model.starshipClass);

        setText(R.id.model, model.model);
        setText(R.id.manufacturer, model.manufacturer);
        setText(R.id.length, FormatUtils.getFormattedLengthM(context, model.length));
        setText(R.id.cargo_capacity, FormatUtils.getFormattedTonnage(context, model.cargoCapacity));
        setText(R.id.cost_in_credits, FormatUtils.getFormattedCredits(context, model.costInCredits));
        setText(R.id.max_atmosphering_speed, FormatUtils.getFormattedSpeedKph(context, model.maxAtmospheringSpeed));
        setText(R.id.crew, FormatUtils.getFormattedNumber(model.crew));
        setText(R.id.passengers, FormatUtils.getFormattedNumber(model.passengers));
        setText(R.id.consumables, FormatUtils.getFormattedNumber(model.consumables));
    }
}

