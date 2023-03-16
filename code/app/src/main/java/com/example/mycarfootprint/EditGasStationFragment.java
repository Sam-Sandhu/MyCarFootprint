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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// Fragment for editing a gas station visit
public class EditGasStationFragment extends DialogFragment { 
    public interface EditGasStationDialogListener { 
        void editGasStationVisit();
    }
    private EditGasStationDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditGasStationDialogListener) {
            listener = (EditGasStationDialogListener) context;
        }
        else {
            throw new RuntimeException(context + " must implement EditGasStationDialogListener"); // Throw an error if the context does not implement the interface
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        MainActivity  mainActivity = (MainActivity) getActivity(); // Get the main activity
        assert mainActivity != null;
        GasStationVisit gasStationVisit= mainActivity.getGasStationVisitObject(); // Get the gas station visit object

        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_gas_station, null);

        EditText gasStationName = view.findViewById(R.id.edit_gas_station_name);
        EditText gasStationDate = view.findViewById(R.id.edit_gas_station_date);
        RadioGroup gasStationTypeRadioGroup = view.findViewById(R.id.edit_gas_station_type_radiogroup);
        EditText gasStationAmount = view.findViewById(R.id.edit_gas_station_amount);
        EditText gasStationPrice = view.findViewById(R.id.edit_gas_station_price);

        // Set the values of the gas station visit object to the edit texts
        gasStationName.setText(gasStationVisit.getGasStationName());
        gasStationDate.setText(gasStationVisit.getDate());
        gasStationAmount.setText(String.valueOf(gasStationVisit.getAmount()));
        gasStationPrice.setText(String.valueOf(gasStationVisit.getPricePerLiter()));

        if (gasStationVisit.getFuelType().equals("Gasoline")){ // Set the correct radio button checked
            RadioButton rbGasoline = view.findViewById(R.id.edit_radiobutton_gasoline);
            rbGasoline.setChecked(true);
        }else{
            RadioButton rbDiesel = view.findViewById(R.id.edit_radiobutton_diesel);
            rbDiesel.setChecked(true);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit a Visit")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog, which) -> {

                    // Get the values from the edit texts and set them to the gas station visit object
                    String name = gasStationName.getText().toString();
                    gasStationVisit.setGasStationName(name);

                    String date = gasStationDate.getText().toString();
                    gasStationVisit.setDate(date);

                    int selectedId = gasStationTypeRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = view.findViewById(selectedId);
                    String type = radioButton.getText().toString();
                    gasStationVisit.setFuelType(type);

                    int amount = Integer.parseInt(gasStationAmount.getText().toString());
                    gasStationVisit.setAmount(amount);

                    Double price = Double.parseDouble(gasStationPrice.getText().toString());
                    price = (double) Math.round(price * 100) / 100; // rounding to 2 decimal places
                    gasStationVisit.setPricePerLiter(price);
                    listener.editGasStationVisit();
                })
                .create();
    }

}

