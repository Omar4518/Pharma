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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private EditText RegisterName, RegisterAdd, RegisterPhone, RegisterEmail, RegisterPass;
    private Button RegisterButton;
    private FirebaseAuth registerAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference RootRef;
    private FirebaseDatabase reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        if(registerAuth.getCurrentUser() != null ){
            SendUserToMainActivity();
            finish();
        }
        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }



    private void CreateNewAccount() {
        String email = RegisterEmail.getText().toString().trim();//ba5od elemail eli katbo
        String password = RegisterPass.getText().toString().trim();//ba5od elpass eli katbo

        if (TextUtils.isEmpty(email)) {//lw sab mkan elemail fady y2olo ektb email
            RegisterEmail.setError("Email Is Required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            RegisterPass.setError("Password Is Required");//lw sab mkan elpass fady y2olo ektb elpass
            return;
        } else {
            loadingBar.setTitle("Creating New Account");//lma y3ml account ytl3 yktblo en elaccount bit3aml
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            registerAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                            {
                                //lw 3aml elaccount s7 ytl3lo massg en elaccount et3aml
                                    reference = FirebaseDatabase.getInstance();
                                    RootRef = reference.getReference("UsersCredentials");
                                    //get all the values from the fields
                                    String currentUser =registerAuth.getCurrentUser().getUid();
                                    String name = RegisterName.getEditableText().toString();
                                    String email = RegisterEmail.getEditableText().toString();
                                    String password = RegisterPass.getEditableText().toString();
                                    String phone = RegisterPhone.getEditableText().toString();
                                    String address = RegisterAdd.getEditableText().toString();
                                    //save in firebase
                                    UserHelper userHelper = new UserHelper(name,email,password,phone,address);
                                    RootRef.child(currentUser).setValue(userHelper);
                                    SendUserToMainActivity();
                                    Toast.makeText(registerActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }
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
         RegisterName = (EditText) findViewById(R.id.text_view_name_register);
        RegisterPhone = (EditText) findViewById(R.id.text_phone_register);
        RegisterAdd = (EditText) findViewById(R.id.text_address_register); //downward casting
        RegisterEmail = (EditText) findViewById(R.id.text_view_email_register);
        RegisterPass = (EditText) findViewById(R.id.text_view_password_register);
        RegisterButton = (Button) findViewById(R.id.button_register);
        loadingBar = new ProgressDialog(this);

    }
    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(registerActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

   /* private void SendUserToPhoneActivity() {
        Intent phoneIntent = new Intent(registerActivity.this,VerifyPhoneNumber.class);
        startActivity(phoneIntent);
        finish();
    }*/
}
