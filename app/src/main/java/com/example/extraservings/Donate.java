package com.example.extraservings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Donate extends AppCompatActivity implements CheckNetwork{

    @Override
    public void handleNetworkUnavailable() {
        Toast.makeText(this, "Network Unavailable. Please check your Network Settings.", Toast.LENGTH_SHORT).show();

    }
    EditText edt_address;
    Button btn_donate;
    TextView quantitynumber;
    ImageButton plusquantity, minusquantity;
    RadioButton radio_breakfast,radio_lunch,radio_dinner;
    private String foodType = "Dinner", quantityNumber;
    int quantity=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        edt_address = findViewById(R.id.edt_address);
        btn_donate = findViewById(R.id.btn_donate);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity  = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);
        radio_breakfast = (RadioButton)findViewById(R.id.radio_breakfast);
        radio_lunch = (RadioButton)findViewById(R.id.radio_lunch);
        radio_dinner = (RadioButton)findViewById(R.id.radio_dinner);

        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity++;
                displayQuantity();

            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // because we dont want the quantity go less than 0
                if (quantity == 1) {
                    Toast.makeText(Donate.this, "Cant decrease quantity < 1", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                }
            }
        });

        quantityNumber=Integer.toString(quantity);

        radio_breakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = radio_breakfast.getText().toString();
                }
            }
        });
        radio_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    foodType =radio_lunch.getText().toString();
                }
            }
        });
        radio_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = radio_dinner.getText().toString();
                }
            }
        });




                btn_donate.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(edt_address.getText().toString()) || edt_address.length() < 5) {
                            displayToast("Please enter the correct address.");
                        } else {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(Donate.this);
                            myDB.addDonar(edt_address.getText().toString().trim(), foodType, quantity, "available");
                        }
                    }
                });

        }




    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }


    private void displayToast(String toastMsg) {
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }

}