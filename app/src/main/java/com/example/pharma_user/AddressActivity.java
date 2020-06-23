package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressActivity extends AppCompatActivity {
    private Button buttonLocation,buttonNext;
    private EditText address,locality,country;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        InitializeFields();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToPharmacyActivity();
            }
        });

        //intialize fusedLocationProviderClient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ActivityCompat.checkSelfPermission(AddressActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission granted
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(AddressActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
            }
        });
    }

    private void SendUserToPharmacyActivity() {
        Intent pharmaIntent = new Intent(AddressActivity.this,ChoosePharmacy.class);
        startActivity(pharmaIntent);
    }

    private void InitializeFields() {
        buttonLocation = (Button) findViewById(R.id.button_location);
        address = (EditText) findViewById(R.id.edit_text_address_user);
        locality = (EditText) findViewById(R.id.edit_text_local_user);
        country = (EditText) findViewById(R.id.edit_text_country);
        buttonNext = (Button) findViewById(R.id.button_next_add);


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //initialize location
                Location location = task.getResult();
                if (location != null) {


                    //initialize Address
                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(AddressActivity.this
                                , Locale.getDefault());

                        List<Address> addresses=geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        //set country name
                        country.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name  :</b><br></font>"
                                        +addresses.get(0).getCountryName()
                        ));

                        //set locality
                        locality.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality  :</b><br></font>"
                                        +addresses.get(0).getLocality()
                        ));

                        //set  Address
                        address.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address  :</b><br></font>"
                                        +addresses.get(0).getAddressLine(0)
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    }
