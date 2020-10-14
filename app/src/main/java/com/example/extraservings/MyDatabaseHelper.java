package com.example.extraservings;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;
    public static final String DATABASE_NAME = "donate.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "my_donation";
    public static final String COLUMN_ID = "_id";
    public static final String  COLUMN_STATUS = "food_status";
    public static final String COLUMN_ADDRESS = "donate_address";
    public static final String COLUMN_TYPE = "food_type";
    public static final String  COLUMN_QUANTITY = "quantity_serves";

    public MyDatabaseHelper open() throws SQLException {
        this.dbHelper = new MyDatabaseHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER);";
        db.execSQL(query);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addDonar(String address, String foodType, int quantity, String foodStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_TYPE, foodType);
        cv.put(COLUMN_QUANTITY, quantity);
        cv.put(COLUMN_STATUS,foodStatus);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Donated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean updateToBooked(String row_id) {
        open();
        Cursor cursor =readAllData();
        if (cursor== null)
        {
            Toast.makeText(context, row_id +"cursor empty", Toast.LENGTH_SHORT).show();

        }

        if(cursor.moveToFirst()){
            Toast.makeText(context, row_id +"cursor", Toast.LENGTH_SHORT).show();

            do{
                Toast.makeText(context, row_id +"String do", Toast.LENGTH_SHORT).show();

                if(row_id.equals(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ID))))
                {
                    Toast.makeText(context, row_id +"check", Toast.LENGTH_SHORT).show();

                    SQLiteDatabase db = this.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_ADDRESS, cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_ADDRESS)));
                    cv.put(COLUMN_TYPE, cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_TYPE)));
                    cv.put(COLUMN_STATUS, "booked");
                    cv.put(COLUMN_QUANTITY, cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_QUANTITY)));

                    long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
                    Toast.makeText(context, result+" Failed", Toast.LENGTH_SHORT).show();

                    if (result == -1) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        db.close();
                        return true;

                    }
                    db.close();
                }
            }while (cursor.moveToNext());
        }
        return false;
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

}
