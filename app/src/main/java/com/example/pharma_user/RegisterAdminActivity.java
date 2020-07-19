package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAdminActivity extends AppCompatActivity {
    private FirebaseUser currentAdmin;
    private EditText RegisterNameAdmin, RegisterAddAdmin ,RegisterPhoneAdmin, RegisterEmailAdmin, RegisterPassAdmin,RegisterPharmaName;
    private Button RegisterButtonAdmin;
    private FirebaseAuth registerAuthAdmin;
    private ProgressDialog loadingBar;
    private DatabaseReference RootReff;
    private FirebaseDatabase referencce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        registerAuthAdmin = FirebaseAuth.getInstance();
        RootReff = FirebaseDatabase.getInstance().getReference();
        if(registerAuthAdmin.getCurrentUser() != null ){
            SendUserToRequestAdminActivity();
            finish();
        }
        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign
        RegisterButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }



    private void CreateNewAccount() {
        String adminEmail = RegisterEmailAdmin.getText().toString().trim();//ba5od elemail eli katbo
        String adminPassword = RegisterPassAdmin.getText().toString().trim();//ba5od elpass eli katbo

        if (TextUtils.isEmpty(adminEmail)) {//lw sab mkan elemail fady y2olo ektb email
            RegisterEmailAdmin.setError("Email Is Required");
            return;
        }
        if (TextUtils.isEmpty(adminPassword)) {
            RegisterPassAdmin.setError("Password Is Required");//lw sab mkan elpass fady y2olo ektb elpass
            return;
        } else {
            loadingBar.setTitle("Creating New Account");//lma y3ml account ytl3 yktblo en elaccount bit3aml
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            registerAuthAdmin.createUserWithEmailAndPassword(adminEmail, adminPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                {
                                    //lw 3aml elaccount s7 ytl3lo massg en elaccount et3aml
                                    referencce = FirebaseDatabase.getInstance();
                                    RootReff = referencce.getReference("AdminsCredentials");
                                    //get all the values from the fields
                                    String currentAdmin =registerAuthAdmin.getCurrentUser().getUid();
                                    String adminName = RegisterNameAdmin.getEditableText().toString();
                                    String adminEmail = RegisterEmailAdmin.getEditableText().toString();
                                    String adminPass = RegisterPassAdmin.getEditableText().toString();
                                    String adminPhone = RegisterPhoneAdmin.getEditableText().toString();
                                    String adminAdd = RegisterAddAdmin.getEditableText().toString();
                                    String pharmaName = RegisterPharmaName.getEditableText().toString();
                                    //save in firebase
                                    AdminHelper adminHelper = new AdminHelper(adminName,adminAdd,adminEmail,adminPass,adminPhone,pharmaName);
                                    RootReff.child(currentAdmin).setValue(adminHelper);
                                    SendUserToRequestAdminActivity();
                                    Toast.makeText(RegisterAdminActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }
                            } else {//lw fe error ytl3lo elerror
                                String message = task.getException().toString();
                                Toast.makeText(RegisterAdminActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


    private void InitializeFields() {//t3ref eli mogod feldesign
        RegisterNameAdmin = (EditText) findViewById(R.id.text_view_name_register_admin);
        RegisterPhoneAdmin = (EditText) findViewById(R.id.text_phone_register_admin);
        RegisterPharmaName = (EditText) findViewById(R.id.text_view_Pharma_name_register_admin);
        RegisterAddAdmin = (EditText) findViewById(R.id.text_address_register_admin); //downward casting
        RegisterEmailAdmin = (EditText) findViewById(R.id.text_view_email_register_admin);
        RegisterPassAdmin = (EditText) findViewById(R.id.text_view_password_register_admin);
        RegisterButtonAdmin = (Button) findViewById(R.id.button_register_admin);
        loadingBar = new ProgressDialog(this);

    }
    private void SendUserToRequestAdminActivity() {
        Intent mainIntent = new Intent(RegisterAdminActivity.this, RequestActivityAdmin.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    }
