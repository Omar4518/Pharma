package com.example.pharma_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class welcomeActivity extends AppCompatActivity {
    private Button loginButton;
    private TextView createNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        InitializeFields();

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterActivity();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginActivity();

            }
        });
    }

    private void SendUserToLoginActivity() {
        Intent registerIntent= new Intent(welcomeActivity.this,LoginActivity.class);
        startActivity(registerIntent);
    }

    private void InitializeFields() {
        loginButton = (Button) findViewById(R.id.login_button_welcome);
        createNewAccount= (TextView) findViewById(R.id.create_new_account_link_welcome);
    }

    private void SendUserToRegisterActivity() {
        Intent registerIntent= new Intent(welcomeActivity.this,registerActivity.class);
        startActivity(registerIntent);
    }
}
