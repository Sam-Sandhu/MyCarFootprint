/*
CMPUT 301 Winter 2023
Assignment 1
Author- Samrathjit Sandhu (Std ID: 1740737)
*/

package com.example.mycarfootprint;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

// Main activity for the app

public class MainActivity extends AppCompatActivity implements AddGasStationFragment.AddGasStationDialogListener,
        EditGasStationFragment.EditGasStationDialogListener {
    private GasStationVisit gasStationVisit;
    private GasStationArrayAdapter gasStationArrayAdapter;
    private ArrayList gasStationList;

    // Add the gas station visit to the list
    @Override
    public void addGasStationVisit(GasStationVisit gasStationVisit) {
        gasStationList.add(gasStationVisit);
        gasStationArrayAdapter.notifyDataSetChanged();
        updateTotalValues();
    }
    // Edit the gas station visit in the list
    @Override
    public void editGasStationVisit() {
        gasStationArrayAdapter.notifyDataSetChanged();
        updateTotalValues();
    }
    // Get the gas station visit object
    public GasStationVisit getGasStationVisitObject() {
        return gasStationVisit;
    }
    private TextView totalCarbonFootprintTextView;
    private TextView totalFuelPriceTextView;

    // Update the total carbon footprint and total fuel price
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateTotalValues() {
        double totalCarbonFootprint = 0;
        double totalFuelPrice = 0;
        for (int i = 0; i < gasStationList.size(); i++) {
            GasStationVisit gasStationVisit = (GasStationVisit) gasStationList.get(i);
            totalCarbonFootprint += gasStationVisit.getCarbonFootprint();
            totalFuelPrice += gasStationVisit.getFuelCost();
        }
        totalCarbonFootprintTextView.setText("Total Carbon Footprint: " + totalCarbonFootprint + " kg");
        totalFuelPriceTextView.setText("Total Fuel Price: $" + String.format("%.2f", totalFuelPrice));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gasStationList = new ArrayList<GasStationVisit>();
        ListView gasStationListView = findViewById(R.id.gas_stations_list_view);
        gasStationArrayAdapter = new GasStationArrayAdapter(this, gasStationList);
        gasStationListView.setAdapter(gasStationArrayAdapter);

        totalCarbonFootprintTextView = findViewById(R.id.text_total_carbon_footprint);
        totalFuelPriceTextView = findViewById(R.id.text_total_fuel_price);

        // Button to add a gas station visit
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddGasStationFragment().show(getSupportFragmentManager(), "Add Gas Station Visit");
        });

        // Edit a gas station visit on single click
        gasStationListView.setOnItemClickListener((adapterView, view, i, l) -> {
          gasStationVisit = (GasStationVisit) gasStationList.get(i);
          new EditGasStationFragment().show(getSupportFragmentManager(), "Edit Gas Station Visit");
        });

        // Delete a gas station visit on long click
        gasStationListView.setOnItemLongClickListener((parent, view, position, id) -> {
            final int itemRemove = position;
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Do you want to delete this entry?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        gasStationList.remove(itemRemove);
                        gasStationArrayAdapter.notifyDataSetChanged();
                        updateTotalValues();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

    }
}
