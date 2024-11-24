package com.example.financeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "finance_database";
    public static final String TABLE_NAME = "transactions_table";

    public static final String COL_ID = "id";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_TYPE = "type";
    public static final String COL_CATEGORY = "category";
    public static final String COL_DATE = "date"; // New column

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // Updated version to 2
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_AMOUNT + " REAL," +
                COL_TYPE + " TEXT," +
                COL_CATEGORY + " TEXT," +
                COL_DATE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_DATE + " TEXT");
        }
    }

    public boolean addNewTransaction(double transactionAmount, String transactionType, String transactionCategory, String transactionDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_AMOUNT, transactionAmount);
        contentValues.put(COL_TYPE, transactionType);
        contentValues.put(COL_CATEGORY, transactionCategory);
        contentValues.put(COL_DATE, transactionDate); // Include date

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor fetchAllTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_DATE + " DESC", null);
        return res;
    }

    public double calculateTotalIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COL_TYPE + " = ?", new String[]{"Income"});
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public double computeTotalExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_AMOUNT + ") FROM " + TABLE_NAME + " WHERE " + COL_TYPE + " = ?", new String[]{"Expense"});
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }
}