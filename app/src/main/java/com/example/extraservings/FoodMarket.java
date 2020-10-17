package com.example.extraservings;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FoodMarket extends AppCompatActivity implements RecyclerViewClickListener{

    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;
    Button btn_request;
    MyDatabaseHelper myDB;
    ArrayList<String> donation_id, donar_address, food_type, quantity_serves,expiry_date;
    CustomAdapter customAdapter;
    String id, address, food, number, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_market);

        recyclerView = findViewById(R.id.recyclerView);
        btn_request = findViewById(R.id.btn_request);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);


        myDB = new MyDatabaseHelper(FoodMarket.this);
        donation_id = new ArrayList<>();
        donar_address = new ArrayList<>();
        food_type = new ArrayList<>();
        quantity_serves = new ArrayList<>();
        expiry_date=new ArrayList<>();

        try {
            storeDataInArrays();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        customAdapter = new CustomAdapter(FoodMarket.this, this, donation_id, donar_address,food_type,quantity_serves,expiry_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FoodMarket.this));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays() throws ParseException {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {

            if(cursor.moveToFirst()){

                do {

                    if(cursor.getString(3).equals("available")){
                        donation_id.add(cursor.getString(0));
                        donar_address.add(cursor.getString(1));
                        food_type.add(cursor.getString(2));
                        quantity_serves.add(cursor.getString(4));
                        expiry_date.add(cursor.getString(5));

                    }
                }while(cursor.moveToNext());
            }
          empty_imageview.setVisibility(View.GONE);
           no_data.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btn_request) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Request Food?");
        builder.setMessage("Are you sure you want to request this donation?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(FoodMarket.this);
                myDB.deleteOneRow(id);
                //Refresh Activity
                Intent intent = new Intent(FoodMarket.this, FoodMarket.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

}