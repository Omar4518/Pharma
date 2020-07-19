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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddressActivity extends AppCompatActivity {
    private Button buttonLocation,buttonNext;
    private EditText Address,locality,country,BuildNo,FloorNo,AppNo;
    FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mAuth = FirebaseAuth.getInstance();
        InitializeFields();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buildNo = BuildNo.getText().toString();
                String floorNo = FloorNo.getText().toString();
                String appNo = AppNo.getText().toString();
                if (TextUtils.isEmpty(buildNo))
                {
                    BuildNo.setError("Please enter Your Building Number");
                    return;
                }
                if (TextUtils.isEmpty(floorNo))
                {
                    FloorNo.setError("Please enter Your Floor No");
                    return;
                }
                if (TextUtils.isEmpty(appNo))
                {
                    AppNo.setError("Please enter Apartment Number");
                    return;
                }
                SendDataToFirebase();

            }
        });

        //intialize fusedLocationProviderClient

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //check permission
                if (ActivityCompat.checkSelfPermission(AddressActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission granted
                    getLocation();
                }
                else {
                    //when permission denied
                    ActivityCompat.requestPermissions(AddressActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });
    }

    private void SendDataToFirebase() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Orders");
        //get all the values from the fields
        String currentAdmin = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String address = Address.getEditableText().toString();
        String buildNo = BuildNo.getEditableText().toString();
        String floorNo = FloorNo.getEditableText().toString();
        String appNo = AppNo.getEditableText().toString();

        //save in firebase
        AddressOrder addressOrder = new AddressOrder(address,buildNo,floorNo,appNo);
        ref.child(currentAdmin).child("Address").setValue(addressOrder);
        SendUserToPharmacyActivity();
    }

    private void SendUserToPharmacyActivity() {
        Intent pharmaIntent = new Intent(AddressActivity.this,ChoosePharmacy.class);
        startActivity(pharmaIntent);
    }

    private void InitializeFields() {
        buttonLocation = (Button) findViewById(R.id.button_location);
        Address = (EditText) findViewById(R.id.edit_text_address_user);
        locality = (EditText) findViewById(R.id.edit_text_local_user);
        country = (EditText) findViewById(R.id.edit_text_country);
        BuildNo = (EditText) findViewById(R.id.edit_text_build_no);
        FloorNo = (EditText) findViewById(R.id.edit_text_floorno);
        AppNo = (EditText) findViewById(R.id.edit_text_apartmentNo);
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
                        Address.setText(Html.fromHtml(
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
