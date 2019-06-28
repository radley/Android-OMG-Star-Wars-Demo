package dev.radley.omgstarwars.view.detailview;


import android.content.Context;
import android.view.LayoutInflater;

import dev.radley.omgstarwars.R;
import dev.radley.omgstarwars.models.SWModel;
import dev.radley.omgstarwars.models.Vehicle;
import dev.radley.omgstarwars.utilities.FormatUtils;

/**
 * Layout for Vehicle details text
 */
public class VehicleDetailView extends DetailView {

    Vehicle model;

    public VehicleDetailView(Context context) {
        super(context);
    }

    public VehicleDetailView(Context context, SWModel model) {
        super(context);

        this.context = context;
        this.model = (Vehicle) model;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_detail_vehicle, this, true);

        populateContent();
    }

    /**
     * Add content to layout
     */
    void populateContent() {

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
