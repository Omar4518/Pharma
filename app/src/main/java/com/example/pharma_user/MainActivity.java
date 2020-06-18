package com.example.pharma_user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pharma_user.Medicine.DrugsActivity;
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

    private final int REQUEST_CODE = 1; //0000000000000000000

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();//34an 23rf eldatabase
    final DatabaseReference reference = database.getReference();
    private Button chosseMedicineButton;  //___________________________________________________


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();//34an agib elemial eli 3aml login w ad5ol bih b3d ellogin
        currentUser = mAuth.getCurrentUser();

        Button buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToAddressActivity();
                sendData();
            }
        });


        chosseMedicineButton = (Button) findViewById( R.id.choose_medicine_btn);//___________________________
        chosseMedicineButton.setOnClickListener( new View.OnClickListener() {//_________________________________________
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrugsActivity.class);
                startActivityForResult( intent , REQUEST_CODE );  //000000000000000000000000000000000000000000000

            }
        } );

    }

    private void SendUserToAddressActivity() {
        Intent addressIntent = new Intent(MainActivity.this, AddressActivity.class);
        startActivity(addressIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //0000000000000000000000
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode==RESULT_OK)
            {
                String result = data.getStringExtra( "returnData" );
                EditText editText = (EditText) findViewById( R.id.text_view_Presc );
                editText.setText( result );
            }
        }        super.onActivityResult( requestCode, resultCode, data );
    }



    @Override//34an lw eluser msh 3aml login yt3lo 3la elwelcome activity
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            SendUserToWelcomeUserActivity();

        }
    }

    private void SendUserToWelcomeUserActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivityUser.class);
        startActivity(loginIntent);
    }

    private void SendUserToLoginUserActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivityUser.class);
        startActivity(loginIntent);
    }

    public void sendData() {
        EditText editTextMedicine = findViewById(R.id.text_view_Presc); // ___XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX اى يبنى ده
        final String Med = editTextMedicine.getText().toString();reference.addListenerForSingleValueEvent(new ValueEventListener() {

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
        getMenuInflater().inflate(R.menu.menus_options_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_options_user) {
            mAuth.signOut();
            SendUserToLoginUserActivity();
        }
        return true;
    }

}

