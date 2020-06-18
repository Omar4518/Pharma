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
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class LoginAdminActivity extends AppCompatActivity {
    private Button LoginButtonAdmin,CreateNewAccountButtonAdmin;
    private EditText UserEmailAdmin, UserPasswordAdmin;
    private TextView ForgetPasswordLinkAdmin;
    private ProgressDialog loadingBar;
    private FirebaseAuth nAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        nAuth = FirebaseAuth.getInstance();


        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign

        CreateNewAccountButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterAdminActivity();
            }
        });
        LoginButtonAdmin.setOnClickListener(new View.OnClickListener() {//zorar eloogin bandah fe 3almethod eli bt3ml login
            @Override
            public void onClick(View v) {
                AllowUserToLogin();

            }
        });
    }

    private void AllowUserToLogin()//elmetod eli bt3ml login
    {
        String adminEmail = UserEmailAdmin.getText().toString();
        String adminPassword = UserPasswordAdmin.getText().toString();

        if (TextUtils.isEmpty(adminEmail))//lw sab mkan elemail fady y2olo ektb email
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(adminPassword))//lw sab mkan elpass fady y2olo ektb elpass
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            nAuth.signInWithEmailAndPassword(adminEmail, adminPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//lw 3aml elaccount mogod ytl3lo massg eno 3aml login
                                SendUserToMainAdminActivity();
                                Toast.makeText(LoginAdminActivity.this, "Logged in successful", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().toString();//lw fe error ytl3lo elerror
                                Toast.makeText(LoginAdminActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                        }
                    });
        }
    }


    private void SendUserToMainAdminActivity() {
        Intent mainIntent = new Intent(LoginAdminActivity.this, MainActivityAdmin.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void InitializeFields() {//t3ref eli mogod feldesign
        LoginButtonAdmin = (Button) findViewById(R.id.login_button_admin);
        CreateNewAccountButtonAdmin=(Button) findViewById(R.id.login_create_new_account_button_admin);
        UserEmailAdmin = (EditText) findViewById(R.id.login_email_admin);
        UserPasswordAdmin= (EditText) findViewById(R.id.login_password_admin);
        ForgetPasswordLinkAdmin = (TextView) findViewById(R.id.forget_password_link_login_admin);
        loadingBar = new ProgressDialog(this);
    }
    private void SendUserToRegisterAdminActivity() {
        Intent registerIntent = new Intent(LoginAdminActivity.this, RegisterAdminActivity.class);
        startActivity(registerIntent);
    }

}
