package com.example.pharma_user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivityUser extends AppCompatActivity {
    private static final int RC_SIGN_IN = 101;
    private Button LoginButtonUser;
    private EditText UserEmailUser, UserPasswordUser;
    private TextView ForgetPasswordLinkUser, CreateNewAccountLinkUser;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton ButtonGoogle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        InitializeFields();//bandah 3la elmethod eli b3rf fiha eli mogod feldesign
        mAuth = FirebaseAuth.getInstance();
             ForgetPasswordLinkUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityUser.this, ForgetPasswordUserActivity.class));
            }
        });
        //google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login_button_user_google:
                        signIn();
                        break;
                    // ...
                }
            }
        });
            CreateNewAccountLinkUser.setOnClickListener(new View.OnClickListener() {
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

    /////////////////////////////////////////////////////////////////////////////google////////////////////// kol ely t7t dh bra el on create ya omaaaar

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivityUser.this, user.getEmail() + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivityUser.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(LoginActivityUser.this, MainActivity.class);
        startActivity(intent);
    }
    /////////////////////////////////////////////////////////////////////////////google//////////////////////


    private void SendUserToRegisterUserActivity() {
        Intent registerIntent = new Intent(LoginActivityUser.this, RegisterActivityUser.class);
        startActivity(registerIntent);
    }

    private boolean AllowUserToLogin()//elmetod eli bt3ml login
    {
        String userEmail = UserEmailUser.getText().toString();
        String userPassword = UserPasswordUser.getText().toString();

        if (userEmail.isEmpty())//lw sab mkan elemail fady y2olo ektb email
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (userPassword.isEmpty())//lw sab mkan elpass fady y2olo ektb elpass
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (!userEmail.endsWith(".com") || !userEmail.contains("@")) {
            Toast.makeText(getApplicationContext(), "please valid E-mail", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
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
        return true;
    }


    private void SendUserToMainUserActivity() {
        Intent mainUserIntent = new Intent(LoginActivityUser.this, MainActivity.class);
        startActivity(mainUserIntent);
    }


    private void InitializeFields() {//t3ref eli mogod feldesign
        LoginButtonUser = (Button) findViewById(R.id.login_button_user);
        ButtonGoogle = (SignInButton) findViewById(R.id.login_button_user_google);
        CreateNewAccountLinkUser = (TextView) findViewById(R.id.login_create_new_account_text_user);
        UserEmailUser = (EditText) findViewById(R.id.login_email_user);
        UserPasswordUser = (EditText) findViewById(R.id.login_password_user);
        ForgetPasswordLinkUser = (TextView) findViewById(R.id.forget_password_link_login_user);
        loadingBar = new ProgressDialog(this);
    }
}
