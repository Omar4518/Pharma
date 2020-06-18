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

public class RegisterActivityUser extends AppCompatActivity {
    private FirebaseUser currentUser;
    private EditText RegisterNameUser, RegisterAddUser ,RegisterPhoneUser, RegisterEmailUser, RegisterPassUser;
    private Button RegisterButtonUser;
    private FirebaseAuth registerAuthUser;
    private ProgressDialog loadingBar;
    private DatabaseReference RootRef;
    private FirebaseDatabase reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerAuthUser = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        if(registerAuthUser.getCurrentUser() != null ){
            SendUserToMainActivity();
            finish();
        }
        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign
        RegisterButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }



    private void CreateNewAccount() {
        String userEmail = RegisterEmailUser.getText().toString().trim();//ba5od elemail eli katbo
        String userPassword = RegisterPassUser.getText().toString().trim();//ba5od elpass eli katbo

        if (TextUtils.isEmpty(userEmail)) {//lw sab mkan elemail fady y2olo ektb email
            RegisterEmailUser.setError("Email Is Required");
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            RegisterPassUser.setError("Password Is Required");//lw sab mkan elpass fady y2olo ektb elpass
            return;
        } else {
            loadingBar.setTitle("Creating New Account");//lma y3ml account ytl3 yktblo en elaccount bit3aml
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            registerAuthUser.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                            {
                                //lw 3aml elaccount s7 ytl3lo massg en elaccount et3aml
                                    reference = FirebaseDatabase.getInstance();
                                    RootRef = reference.getReference("UsersCredentials");
                                    //get all the values from the fields
                                    String currentUser =registerAuthUser.getCurrentUser().getUid();
                                    String name = RegisterNameUser.getEditableText().toString();
                                    String email = RegisterEmailUser.getEditableText().toString();
                                    String password = RegisterPassUser.getEditableText().toString();
                                    String phone = RegisterPhoneUser.getEditableText().toString();
                                    String address = RegisterAddUser.getEditableText().toString();
                                    //save in firebase
                                    UserHelper userHelper = new UserHelper(name,email,password,phone,address);
                                SendUserToMainActivity();
                                RootRef.child(currentUser).setValue(userHelper);
                                Toast.makeText(RegisterActivityUser.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }
                            } else {//lw fe error ytl3lo elerror
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivityUser.this, "Error" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                    }
                         }
                    });
        }
    }


    private void InitializeFields() {//t3ref eli mogod feldesign
        RegisterNameUser = (EditText) findViewById(R.id.text_view_name_register_user);
        RegisterPhoneUser = (EditText) findViewById(R.id.text_phone_register_user);
        RegisterAddUser = (EditText) findViewById(R.id.text_address_register_user); //downward casting
        RegisterEmailUser = (EditText) findViewById(R.id.text_view_email_register_user);
        RegisterPassUser = (EditText) findViewById(R.id.text_view_password_register_user);
        RegisterButtonUser = (Button) findViewById(R.id.button_register_user);
        loadingBar = new ProgressDialog(this);

    }
    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivityUser.this, MainActivity.class);
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
