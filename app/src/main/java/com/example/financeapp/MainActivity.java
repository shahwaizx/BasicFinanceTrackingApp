package com.example.financeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView totalBalanceTextView, totalIncomeTextView, totalExpenseTextView;
    DBHelper dbHelper;
    double totalIncome, totalExpense, totalBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalBalanceTextView = findViewById(R.id.totalBalanceTextView);
        totalIncomeTextView = findViewById(R.id.totalIncomeTextView);
        totalExpenseTextView = findViewById(R.id.totalExpenseTextView);

        dbHelper = new DBHelper(this);

        // Load TransactionsListFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TransactionListFragment transactionListFragment = new TransactionListFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, transactionListFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTotals();
    }

    public void updateTotals() {
        totalIncome = dbHelper.calculateTotalIncome();
        totalExpense = dbHelper.computeTotalExpense();
        totalBalance = totalIncome - totalExpense;

        totalBalanceTextView.setText("Balance: $" + totalBalance);
        totalIncomeTextView.setText("Income: $" + totalIncome);
        totalExpenseTextView.setText("Expense: $" + totalExpense);
    }
}
