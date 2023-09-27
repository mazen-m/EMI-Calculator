package com.example.emi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.emi.databinding.FragmentResultBinding;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // By default, clear the result output fields.
        clearResult();

        Bundle pExtras = getActivity().getIntent().getExtras();

        // Check if the activity was started with an intent which contained values.
        if (pExtras != null) {

            // Grab the values passed to the intent.
            double nPrincipal = pExtras.getDouble("principal");
            double nRate = pExtras.getDouble("rate");
            double nTerm = pExtras.getDouble("term");

            // Calculate the EMI based on the input values.
            double nEMI = calculateEMI(
                    nPrincipal,
                    (nRate / 12) / 100,
                    (int) nTerm * 12
            );

            // Display the output result.
            updateResult(nEMI);

        }

        // Create "On Click" event listener for the Clear Button.
        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Start the calculator activity again with an empty intent.
                Intent pIntent = new  Intent(getActivity().getBaseContext(), CalculatorActivity.class);
                startActivity(pIntent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private double getRoundedValue(double nValue) {

        // Round the double value to two decimal places.
        return (double) Math.round(nValue * 100) / 100;
    }

    private void updateResult(double nEMI) {

        // Update the result fields to show the calculated monthly installment.
        binding.textPreResult.setText("Monthly Installment");
        binding.textResult.setText("$" + getRoundedValue(nEMI));

        // Show the Clear Button.
        binding.buttonClear.setVisibility(View.VISIBLE);
    }

    private void clearResult() {

        // Clear the result output.
        binding.textPreResult.setText("");
        binding.textResult.setText("");

        // Hide the Clear Button.
        binding.buttonClear.setVisibility(View.INVISIBLE);
    }

    private double calculateEMI(double nPrincipal, double nInterestRate, int nMonths) {

        // Let P = Principal, R = Interest Rate, N = Term in Months
        // Calculate the EMI = [P x R x (1 + R)^N] / [(1 + R)^N - 1]
        return nPrincipal * nInterestRate * (Math.pow((1 + nInterestRate), nMonths)
                / (Math.pow((1 + nInterestRate), nMonths) - 1));
    }

}