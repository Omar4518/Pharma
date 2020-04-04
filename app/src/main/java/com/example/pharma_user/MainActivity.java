package com.example.pharma_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();//34an 23rf eldatabase
    final DatabaseReference reference = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        getMenuInflater().inflate(R.menu.menus_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.main_logout_options)
        {
          mAuth.signOut();
          SendUserToLoginActivity();
        }
        return true;
    }
}