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


        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {

        String email = RegisterEmail.getText().toString();//ba5od elemail eli katbo
        String password = RegisterPass.getText().toString();//ba5od elpass eli katbo

        if (TextUtils.isEmpty(email)) {//lw sab mkan elemail fady y2olo ektb email
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {//lw sab mkan elpass fady y2olo ektb elpass
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
        } else {
            loadingBar.setTitle("Creating New Account");//lma y3ml account ytl3 yktblo en elaccount bit3aml
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            registerAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//lw 3aml elaccount s7 ytl3lo massg en elaccount et3aml
                                SendUserToMainActivity();
                                Toast.makeText(registerActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            } else {//lw fe error ytl3lo elerror
                                String message = task.getException().toString();
                                Toast.makeText(registerActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


    private void InitializeFields() {//t3ref eli mogod feldesign
         /*RegisterName = (EditText) findViewById(R.id.text_view_name_register);
        RegisterAdd = (EditText) findViewById(R.id.text_address_register);
        RegisterPhone = (EditText)//downward casting findViewById(R.id.text_view_phone_register);*/
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
