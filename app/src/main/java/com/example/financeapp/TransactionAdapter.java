package com.example.financeapp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;

    public TransactionAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        cursor.moveToPosition(position);
        return cursor;
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COL_ID));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (cursor.moveToPosition(position)) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.list_item_transaction, parent, false);
            }

            TextView amountTextView = convertView.findViewById(R.id.transactionAmountTextView);
            TextView typeTextView = convertView.findViewById(R.id.transactionTypeTextView);
            TextView categoryTextView = convertView.findViewById(R.id.transactionCategoryTextView);
            TextView dateTextView = convertView.findViewById(R.id.transactionDateTextView); // New TextView

            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COL_AMOUNT));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_TYPE));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_DATE));

            amountTextView.setText("$" + amount);
            typeTextView.setText(type);
            categoryTextView.setText(category);
            dateTextView.setText(date); // Set date
        }

        return convertView;
    }
}