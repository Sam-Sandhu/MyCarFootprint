package com.example.mycarfootprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// Fragment for adding a new gas station visit
public class AddGasStationFragment extends DialogFragment { 
    interface AddGasStationDialogListener {
        void addGasStationVisit(GasStationVisit gasStationVisit);
    }
    private AddGasStationDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddGasStationDialogListener) {
            listener = (AddGasStationDialogListener) context;
        }
        else {
            throw new RuntimeException(context + " must implement AddGasStationDialogListener"); // Throw an error if the context does not implement the interface
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_gas_station, null);
        EditText gasStationName = view.findViewById(R.id.add_gas_station_name);
        EditText gasStationDate = view.findViewById(R.id.add_gas_station_date);
        RadioGroup gasStationTypeRadioGroup = view.findViewById(R.id.add_gas_station_type_radiogroup);
        EditText gasStationAmount = view.findViewById(R.id.add_gas_station_amount);
        EditText gasStationPrice = view.findViewById(R.id.add_gas_station_price);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a Visit")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    
                    // Get the values from the fields
                    String name = gasStationName.getText().toString();
                    String date = gasStationDate.getText().toString();

                    int selectedId = gasStationTypeRadioGroup.getCheckedRadioButtonId(); // get selected radio button from radioGroup

                    RadioButton radioButton = view.findViewById(selectedId);

                    String type = radioButton.getText().toString();

                    String amount_string = gasStationAmount.getText().toString();
                    String price_per_liter_string = gasStationPrice.getText().toString();

                    // Check if all the fields are filled out
                    if (name.isEmpty() || date.isEmpty() || type.isEmpty() || amount_string.isEmpty() || price_per_liter_string.isEmpty()) {
                        Toast.makeText(builder.getContext(), "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
                    }
                    
                    int amount = (int) Double.parseDouble(amount_string);
                    double price = Double.parseDouble(price_per_liter_string);
                    price = (double) Math.round(price * 100) / 100; // Round to 2 decimal places

                    listener.addGasStationVisit(new GasStationVisit(name, date, type, amount, price)); // Add the new gas station visit
                })
                .create();
    }

}
