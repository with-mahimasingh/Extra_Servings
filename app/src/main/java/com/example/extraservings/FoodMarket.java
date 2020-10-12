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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodMarket extends AppCompatActivity implements RecyclerViewClickListener{

    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;
    Button btn_request;
    MyDatabaseHelper myDB;
    ArrayList<String> donation_id, donar_address, food_type, quantity_serves;
    CustomAdapter customAdapter;
    String id, address, food, number;

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

        storeDataInArrays();

        customAdapter = new CustomAdapter(FoodMarket.this, this, donation_id, donar_address,food_type, quantity_serves);
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

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            if(cursor.moveToFirst()){
                do {
                donation_id.add(cursor.getString(0));
                donar_address.add(cursor.getString(1));
                food_type.add(cursor.getString(2));
                quantity_serves.add(cursor.getString(3));
            }while(cursor.moveToNext());
            }
          empty_imageview.setVisibility(View.GONE);
           no_data.setVisibility(View.GONE);
        }


      /*  btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Sent request!",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(FoodMarket.this, UserActivity.class));
              //  final DonationModel donationModel1= new DonationModel();

                //Intent intent = new Intent(FoodMarket.this, UserActivity.class);
                //startActivity(intent);

            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btn_request) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

   /* void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("address") &&
                getIntent().hasExtra("food") && getIntent().hasExtra("number")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            address = getIntent().getStringExtra("address");
            food = getIntent().getStringExtra("food");
            number = getIntent().getStringExtra("number");

            //Setting Intent Data
            //address.setText(address);
            //author_input.setText(food);
            //pages_input.setText(number);
            //Log.d("stev", title+" "+author+" "+pages);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }*/
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