package com.example.emi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.emi.databinding.FragmentCalculatorBinding;
import com.google.android.material.snackbar.Snackbar;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // By default, clear all input fields.
        clearInput();

        // Create "On Click" event for the Calculate Button.
        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the values from all required input fields.
                double nPrincipal = getDouble(binding.inputPrincipal);
                double nRate = getDouble(binding.inputRate);
                double nTerm = getDouble(binding.inputTerm);

                // Check if fields are entered correctly, otherwise display error message.
                if (nPrincipal <= 0 || nRate <= 0 || nTerm <= 0) {

                    Snackbar.make(view, "Please ensure all fields are filled out correctly.", Snackbar.LENGTH_LONG)
                            .setAnchorView(R.id.nav_host_fragment_content_calculator)
                            .setDuration(2000)
                            .setAction("Action", null).show();

                    return;
                }

                // Create an intent for the result activity.
                Intent pIntent = new  Intent(getActivity().getBaseContext(), ResultActivity.class);

                // Pass the input values of principal, rate, and term to the intent.
                pIntent.putExtra("principal", nPrincipal);
                pIntent.putExtra("rate", nRate);
                pIntent.putExtra("term", nTerm);

                // Start the result activity with the defined intent.
                startActivity(pIntent);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getString(android.widget.EditText editText) {

        // Get the value of the input field as a string.
        return editText.getText().toString();
    }

    private double getDouble(android.widget.EditText editText) {

        // Get the string value of an editable text field.
        String sValue = getString(editText);

        // If the field is empty, return 0, otherwise return the double value of the string.
        return sValue.isEmpty() ? 0 : Double.valueOf(sValue);
    }

    private void clearInput() {

        // Clear all of the input fields.
        binding.inputPrincipal.setText("");
        binding.inputRate.setText("");
        binding.inputTerm.setText("");

    }

}