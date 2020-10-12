package com.example.extraservings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Help extends AppCompatActivity {


    TextView txt_select_location;
    EditText edt_address;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        txt_select_location= findViewById(R.id.txt_select_location);
        edt_address= findViewById(R.id.edt_address);
        btn_submit= findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Details Accepted!Now you are redirected to the Food Market!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Help.this, FoodMarket.class));

            }
        });


    }
}