package com.example.pharma_user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private EditText RegisterName, RegisterAdd, RegisterPhone, RegisterEmail, RegisterPass;
    private Button RegisterButton;
    private FirebaseAuth registerAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerAuth = FirebaseAuth.getInstance();


        InitializeFields();
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {

        String email = RegisterEmail.getText().toString();
        String password = RegisterPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
        } else {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            registerAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SendUserToMainActivity();
                                Toast.makeText(registerActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(registerActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


    private void InitializeFields() {
        RegisterName = (EditText) findViewById(R.id.text_view_name_register);
        RegisterAdd = (EditText) findViewById(R.id.text_address_register);
        RegisterPhone = (EditText) findViewById(R.id.text_view_phone_register);
        RegisterEmail = (EditText) findViewById(R.id.text_view_email_register);
        RegisterPass = (EditText) findViewById(R.id.text_view_password_register);
        RegisterButton = (Button) findViewById(R.id.button_register);
        loadingBar = new ProgressDialog(this);


    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(registerActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
