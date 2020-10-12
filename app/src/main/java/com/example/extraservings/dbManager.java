package com.example.extraservings;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public class dbManager {
    private Context context;
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private StatusCheckAdapter adapter;
    private static final String TAG = "DBManager";
    private Cursor cursor;

    public dbManager(Context c) {
        this.context = c;
    }

    public dbManager open() throws SQLException {
        this.dbHelper = new MyDatabaseHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public boolean addToBooked(DonationModel donationModel) {
        open();
        Cursor cursor =getBookedList();

        if(cursor.moveToFirst()){
            do{
                if(donationModel.getAddress().equals(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ID))))
                {
                    Toast.makeText(this.context, "Already exists in Favorites", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }while (cursor.moveToNext());
        }
        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.COLUMN_ID, donationModel.getId());
        values.put(MyDatabaseHelper.COLUMN_TYPE, donationModel.getFood());
        values.put(MyDatabaseHelper.COLUMN_QUANTITY, donationModel.getQuantity());
        values.put(MyDatabaseHelper.COLUMN_ADDRESS, donationModel.getAddress());

        this.database.insert(MyDatabaseHelper.TABLE_NAME, null, values);
        close();
        return true;
    }

    public void deleteFavorite(DonationModel donationModel) {
        this.database.delete(MyDatabaseHelper.DATABASE_NAME, MyDatabaseHelper.COLUMN_ID+ "=" + donationModel.getId(), null);
    }

    public Cursor getBookedList() {

        String[] Columns = {MyDatabaseHelper.DATABASE_NAME, MyDatabaseHelper.TABLE_NAME, MyDatabaseHelper.COLUMN_ID, MyDatabaseHelper.COLUMN_TYPE,
                MyDatabaseHelper.COLUMN_QUANTITY, MyDatabaseHelper.COLUMN_ADDRESS
        };

        cursor = this.database.query(MyDatabaseHelper.TABLE_NAME, Columns, null, null, null, null, null);

        return cursor;
    }
}