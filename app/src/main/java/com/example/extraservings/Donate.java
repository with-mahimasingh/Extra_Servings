package com.example.extraservings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.List;
import java.util.Locale;

public class Donate extends AppCompatActivity implements LocationListener{

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
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


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


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();


        }

    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(Donate.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            edt_address.setText(addresses.get(0).getAddressLine(0));

        } catch (Exception e) {

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }


    private void displayToast(String toastMsg) {
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }

}