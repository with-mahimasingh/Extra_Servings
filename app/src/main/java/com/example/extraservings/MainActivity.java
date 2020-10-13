package com.example.extraservings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    CardView donate;
    CardView foodmarket;
    CardView help;
    CardView search;
    ImageButton btn_userprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        donate = findViewById(R.id.donate);
        foodmarket = findViewById(R.id.foodmarket);
        help = findViewById(R.id.help);
        search = findViewById(R.id.search);
        btn_userprofile=findViewById(R.id.btn_userprofile);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Donate.class));

            }
        });
        foodmarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodMarket.class));

            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Help.class));

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CheckNeedy.class));

            }
        });

        btn_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), UserActivity.class);
                intent.putExtra("ID","-1");
                startActivity(intent);

            }
        });
    }
}