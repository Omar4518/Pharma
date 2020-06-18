package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPaswwordUserActivity extends AppCompatActivity {
    private EditText userEmail;
    private Button sendEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_paswword_user);

        userEmail = findViewById(R.id.email1);
        sendEmail = findViewById(R.id.sendemail1);
        mAuth = FirebaseAuth.getInstance();
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgetPaswwordUserActivity.this, "please enter your email", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgetPaswwordUserActivity.this, "please visit your email to reset your pass ", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPaswwordUserActivity.this,LoginActivityUser.class));
                            }
                            else
                            {
                                String message =task.getException().getMessage();
                                Toast.makeText(ForgetPaswwordUserActivity.this, "Error", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
    }
}