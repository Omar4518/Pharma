package com.example.pharma_user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.annotation.Nullable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pharma_user.Medicine.DrugsActivity;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 1; //0000000000000000000
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();//34an 23rf eldatabase
    DatabaseReference reference = database.getReference();
    private Button chooseMedicineButton,btn_choose;
    private ImageView imageView;
    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //___________________________________________________


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();//34an agib elemial eli 3aml login w ad5ol bih b3d ellogin
        currentUser = mAuth.getCurrentUser();

        Button buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToAddressActivity();
                sendData();
            }
        });


        chooseMedicineButton = (Button) findViewById(R.id.choose_medicine_btn);//___________________________
        chooseMedicineButton.setOnClickListener(new View.OnClickListener() {//_________________________________________
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrugsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);  //000000000000000000000000000000000000000000000

            }
        });


        // Photo0o0o0o0o0o0o
        btn_choose = findViewById(R.id.bt_choose_image);
        imageView = findViewById(R.id.image_view);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }



    //photo
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);

    }

   /* @Override
    protected void onActivityResult(int requestCodee, int resultCodee, @Nullable Intent dataa) {
        super.onActivityResult(requestCodee, resultCodee, dataa);
        if (requestCodee == 1 && resultCodee == RESULT_OK && dataa != null && dataa.getData() != null) {
            filePath = dataa.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }*/


    private void SendUserToAddressActivity() {
        Intent addressIntent = new Intent(MainActivity.this, AddressActivity.class);
        startActivity(addressIntent);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //0000000000000000000000
       if (requestCode == REQUEST_CODE) {
           if (resultCode == RESULT_OK) {
               assert data != null;
               String result = data.getStringExtra("returnData");
               EditText editText = (EditText) findViewById(R.id.text_view_Presc);
               editText.setText(result);
           }

       }
       if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
           filePath = data.getData();

           try {
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
               imageView.setImageBitmap(bitmap);
           } catch (IOException e) {
               e.printStackTrace();
           }
           super.onActivityResult(requestCode, resultCode, data);
       }
   }


    @Override//34an lw eluser msh 3aml login yt3lo 3la elwelcome activity
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            SendUserToWelcomeUserActivity();

        }
    }

    @Override
    public void onBackPressed()
    {
        super.onDestroy();
    }

    private void SendUserToWelcomeUserActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivityUser.class);
        startActivity(loginIntent);
    }

    private void SendUserToLoginUserActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivityUser.class);
        startActivity(loginIntent);
    }


    public void sendData() {
        EditText editTextMedicine = findViewById(R.id.text_view_Presc); // ___XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX اى يبنى ده
        String Med = editTextMedicine.getText().toString();
        reference = database.getReference("Orders");
        String currentUser=mAuth.getCurrentUser().getUid();
        String Medicine = editTextMedicine.getEditableText().toString();
        MedicineOrder medicineOrder = new MedicineOrder(Medicine);
        reference.child(currentUser).setValue(medicineOrder);


        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading......");
            progressDialog.show();


            StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + " % ");
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menus_options_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_options_user) {
            mAuth.signOut();
            SendUserToLoginUserActivity();
            finish();
        }
        return true;
    }


}

