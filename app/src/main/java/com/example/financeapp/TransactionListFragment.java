package com.example.financeapp;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ListView;

public class TransactionListFragment extends Fragment {

    ListView transactionListView;
    TransactionAdapter transactionAdapter;
    DBHelper dbHelper;
    Button addTransactionButton;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        transactionListView = view.findViewById(R.id.transactionListView);
        addTransactionButton = view.findViewById(R.id.addTransactionButton);

        dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.fetchAllTransactions();

        transactionAdapter = new TransactionAdapter(getContext(), cursor);
        transactionListView.setAdapter(transactionAdapter);

        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTransactionFragment addTransactionFragment = new AddTransactionFragment();
                addTransactionFragment.setTargetFragment(TransactionListFragment.this, 1);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, addTransactionFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTransactionList();
    }

    public void refreshTransactionList() {
        Cursor cursor = dbHelper.fetchAllTransactions();
        transactionAdapter.swapCursor(cursor);

        // Update totals in MainActivity
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateTotals();
        }
    }
}