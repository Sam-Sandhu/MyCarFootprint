package com.example.mycarfootprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

// Adapter for the list view of gas station visits
public class GasStationArrayAdapter extends ArrayAdapter<GasStationVisit> {
    public GasStationArrayAdapter(Context context, ArrayList<GasStationVisit> gas_stations) {
        super(context, 0, gas_stations);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull
    ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        }
        else {
            view = convertView;
        }

        // Get the gas station visit and the text views
        GasStationVisit gasStationVisit = getItem(position);
        TextView gasStationName = view.findViewById(R.id.gas_station_name);
        TextView gasStationType = view.findViewById(R.id.gas_station_type);
        TextView gasStationVisitDate = view.findViewById(R.id.gas_station_date);
        TextView gasStationFuelCost = view.findViewById(R.id.gas_station_total_price);
        TextView gasStationFootprint = view.findViewById(R.id.gas_station_footprint);

        // Set the text of the text views
        gasStationName.setText(gasStationVisit.getGasStationName());
        gasStationType.setText(gasStationVisit.getFuelType());
        gasStationVisitDate.setText(gasStationVisit.getDate());

        gasStationFuelCost.setText(String.format("%.2f", gasStationVisit.getFuelCost())); // Format the double to 2 decimal places
        gasStationFootprint.setText(Double.toString(gasStationVisit.getCarbonFootprint()) + " kg");

        return view;
    }

}
