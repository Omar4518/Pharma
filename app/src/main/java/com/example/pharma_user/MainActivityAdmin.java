package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivityAdmin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String currentUser;
    final FirebaseDatabase databasse = FirebaseDatabase.getInstance();
    DatabaseReference referencce,requestRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        mAuth = FirebaseAuth.getInstance();
        currentUser= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
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
}

