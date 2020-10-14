package com.example.extraservings;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;
    Button btn_request;
    MyDatabaseHelper myDB;
    ArrayList<String> donation_id, donar_address, food_type, quantity_serves;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.recyclerView);
        btn_request = findViewById(R.id.btn_request);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);


        myDB = new MyDatabaseHelper(UserActivity.this);
        donation_id = new ArrayList<>();
        donar_address = new ArrayList<>();
        food_type = new ArrayList<>();
        quantity_serves = new ArrayList<>();

        String id = getIntent().getStringExtra("ID");
        if(!id.equals("-1")) {

        Integer id_toUpdate = Integer.parseInt(id);

            boolean isUpdated = myDB.updateToBooked(id);

            if (isUpdated) {
                Toast.makeText(getApplicationContext(), id_toUpdate + " booked", Toast.LENGTH_SHORT).show();
            }
             else{
                    Toast.makeText(getApplicationContext(), "Error in booking", Toast.LENGTH_SHORT).show();
                }
            }
            storeDataInArrays();

            userAdapter = new UserAdapter(UserActivity.this, this, donation_id, donar_address, food_type, quantity_serves);
            recyclerView.setAdapter(userAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(3).equals("booked") || cursor.getString(3).equals("delivered")) {

                        donation_id.add(cursor.getString(0));
                        donar_address.add(cursor.getString(1));
                        food_type.add(cursor.getString(2));
                        quantity_serves.add(cursor.getString(4));
                    }
                } while (cursor.moveToNext());
                empty_imageview.setVisibility(View.GONE);
                no_data.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // if(item.getItemId() == R.id.delete_all){
        //   confirmDialog();
        //}
        return super.onOptionsItemSelected(item);
    }


}