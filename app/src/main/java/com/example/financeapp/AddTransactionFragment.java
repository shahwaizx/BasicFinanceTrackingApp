package com.example.financeapp;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.DatePicker;
import java.util.Calendar;

    public class AddTransactionFragment extends Fragment {

        EditText amountEditText, categoryEditText, dateEditText;
        Spinner typeSpinner;
        Button saveTransactionButton;
        DBHelper dbHelper;

        public AddTransactionFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);

            amountEditText = view.findViewById(R.id.amountEditText);
            categoryEditText = view.findViewById(R.id.categoryEditText);
            dateEditText = view.findViewById(R.id.dateEditText); // Initialize dateEditText
            typeSpinner = view.findViewById(R.id.typeSpinner);
            saveTransactionButton = view.findViewById(R.id.saveTransactionButton);

            dbHelper = new DBHelper(getContext());

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.transaction_types, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(adapter);

            // Set up DatePickerDialog
            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int dayOfMonth) {
                                    String date = (selectedMonth + 1) + "/" + dayOfMonth + "/" + selectedYear;
                                    dateEditText.setText(date);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
            });

            saveTransactionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String amountString = amountEditText.getText().toString();
                    String category = categoryEditText.getText().toString();
                    String type = typeSpinner.getSelectedItem().toString();
                    String date = dateEditText.getText().toString();

                    if (TextUtils.isEmpty(amountString) || TextUtils.isEmpty(category) || TextUtils.isEmpty(date)) {
                        Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double amount = Double.parseDouble(amountString);

                    boolean isInserted = dbHelper.addNewTransaction(amount, type, category, date);

                    if (isInserted) {
                        Toast.makeText(getContext(), "Transaction Added", Toast.LENGTH_SHORT).show();

                        getActivity().getSupportFragmentManager().popBackStack();

                    } else {
                        Toast.makeText(getContext(), "Error Adding Transaction", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return view;
        }
    }