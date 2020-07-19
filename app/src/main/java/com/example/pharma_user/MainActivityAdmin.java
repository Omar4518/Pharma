package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityAdmin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button AcceptButton,DenyButton;
    private FirebaseUser currentUser;
    final FirebaseDatabase databasse = FirebaseDatabase.getInstance();
    final DatabaseReference referencce = databasse.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menus_options_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_options_admin){
            mAuth.signOut();
            SendUserToLoginAdminActivity();
        }
        return true;
    }

    private void SendUserToLoginAdminActivity() {
        Intent loginIntent = new Intent(MainActivityAdmin.this, LoginAdminActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            SendUserToWelcomeActivity();

        }
    }

    private void SendUserToWelcomeActivity() {
        Intent loginIntent = new Intent(MainActivityAdmin.this, LoginAdminActivity.class);
        startActivity(loginIntent);
    }
}

