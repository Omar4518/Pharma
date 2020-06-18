package com.example.pharma_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    private Button loginButtonUser,loginButtonAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        InitializeFields();

        loginButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginUserActivity();
            }
        });
        loginButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginAdminActivity();
            }
        });
    }

    private void SendUserToLoginUserActivity() {
        Intent registerIntent= new Intent(WelcomeActivity.this, LoginActivityUser.class);
        startActivity(registerIntent);
    }

    private void InitializeFields() {
        loginButtonAdmin = (Button) findViewById(R.id.login_button_admin);
        loginButtonUser= (Button) findViewById(R.id.login_button_user);
    }

    private void SendUserToLoginAdminActivity() {
        Intent registerIntent= new Intent(WelcomeActivity.this, LoginAdminActivity.class);
        startActivity(registerIntent);
    }
}
