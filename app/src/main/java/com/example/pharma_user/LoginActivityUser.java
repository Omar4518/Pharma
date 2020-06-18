package com.example.pharma_user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityUser extends AppCompatActivity {
    private Button LoginButtonUser,CreateNewAccountButtonUser;
    private EditText UserEmailUser, UserPasswordUser;
    private TextView ForgetPasswordLinkUser;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        mAuth = FirebaseAuth.getInstance();
        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign

        ForgetPasswordLinkUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToForgetPasswordActivity();
           }
        });
        CreateNewAccountButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterUserActivity();
            }
        });
        LoginButtonUser.setOnClickListener(new View.OnClickListener() {//zorar eloogin bandah fe 3almethod eli bt3ml login
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });
    }

    private void SendUserToForgetPasswordActivity() {
        Intent forgetPassIntent = new Intent(LoginActivityUser.this,ForgetPasswordUserActivity.class);
        startActivity(forgetPassIntent);
    }

    private void SendUserToRegisterUserActivity() {
        Intent registerIntent=new Intent(LoginActivityUser.this,RegisterActivityUser.class);
        startActivity(registerIntent);
    }

    private void AllowUserToLogin()//elmetod eli bt3ml login
    {
        String userEmail = UserEmailUser.getText().toString();
        String userPassword = UserPasswordUser.getText().toString();

        if (TextUtils.isEmpty(userEmail))//lw sab mkan elemail fady y2olo ektb email
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userPassword))//lw sab mkan elpass fady y2olo ektb elpass
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//lw 3aml elaccount mogod ytl3lo massg eno 3aml login
                                SendUserToMainUserActivity();
                                Toast.makeText(LoginActivityUser.this, "Logged in successful", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().toString();//lw fe error ytl3lo elerror
                                Toast.makeText(LoginActivityUser.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                        }
                    });
        }
    }

    private void SendUserToMainUserActivity() {
        Intent mainUserIntent = new Intent(LoginActivityUser.this,MainActivity.class);
        startActivity(mainUserIntent);
    }


    private void InitializeFields() {//t3ref eli mogod feldesign
        LoginButtonUser = (Button) findViewById(R.id.login_button_user);
        CreateNewAccountButtonUser=(Button) findViewById(R.id.login_create_new_account_button_user);
        UserEmailUser = (EditText) findViewById(R.id.login_email_user);
        UserPasswordUser= (EditText) findViewById(R.id.login_password_user);
        ForgetPasswordLinkUser = (TextView) findViewById(R.id.forget_password_link_login_user);
        loadingBar = new ProgressDialog(this);
    }


}
