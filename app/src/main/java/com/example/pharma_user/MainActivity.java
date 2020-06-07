package com.example.pharma_user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();//34an 23rf eldatabase
    final DatabaseReference reference = database.getReference();
    Button btlocation;
    TextView  textView2, textView3;
    EditText editText;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign variable
        btlocation = findViewById(R.id.bt_id);
        editText = findViewById(R.id.e1);
        textView2 = findViewById(R.id.locality);
        textView3 = findViewById(R.id.cname);

        //intialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission granted
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
            }
        });


        mAuth = FirebaseAuth.getInstance();//34an agib elemial eli 3aml login w ad5ol bih b3d ellogin
        currentUser = mAuth.getCurrentUser();

        Button buttonConfirm = findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
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
                        Geocoder geocoder = new Geocoder(MainActivity.this
                                , Locale.getDefault());

                        List<Address> addresses=geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        //set country name
                        textView3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name  :</b><br></font>"
                                +addresses.get(0).getCountryName()
                        ));

                        //set locality
                        textView2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality  :</b><br></font>"
                                        +addresses.get(0).getLocality()
                        ));

                        //set  Address
                        editText.setText(Html.fromHtml(
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

    @Override//34an lw eluser msh 3aml login yt3lo 3la elwelcome activity
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            SendUserToWelcomeActivity();

        }
    }

    private void SendUserToWelcomeActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void sendData() {
        TextView textViewMedicine = findViewById(R.id.text_view_Presc);
        final String Med = textViewMedicine.getText().toString();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override//basagl elorders felfirebase
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.child("Orders").child("Medicine").setValue(Med).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "done", Toast.LENGTH_LONG);
                    }
                });
                reference.child("Orders").child("Location");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menus_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_options) {
            mAuth.signOut();
            SendUserToLoginActivity();
        }
        return true;
    }
}