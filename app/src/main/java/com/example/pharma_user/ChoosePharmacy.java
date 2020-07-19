package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;
import java.util.Dictionary;
import java.util.Hashtable;

public class ChoosePharmacy extends AppCompatActivity {
    private Button submitOrder;
    private String CurrentState,senderUserId;
    final String admin_id;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef,RequestRef;

    public ChoosePharmacy() {
        admin_id = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pharmacy);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("UsersCredentials");
        RequestRef = FirebaseDatabase.getInstance().getReference().child("Requests");



            senderUserId=mAuth.getCurrentUser().getUid();
            TextView textView = findViewById(R.id.text_view_number_pharma);
            CurrentState = "new";


        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ChoosePharmacy.this,
        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.e("","");
        mySpinner.setAdapter(myAdapter);

        submitOrder = (Button) findViewById(R.id.button_submit_order);
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click();
            }
        });


    }

    //make alert dialog
    public void Click() {
        Hashtable<String, String> my_dict = new Hashtable<String, String>();
        my_dict.put("pharma1", "tkgTYkA0z9aE4Nz7Bz5AnuWg9pf2\n");
        my_dict.put("pharma2", "9taokabTevN5Z8tpY3ynJkVXaHy2");
        my_dict.put("pharma3", "lJROz0xprGRLnoOAYPplZpTp8573");

        Spinner mySpinner = findViewById(R.id.spinner1);
        final String text = mySpinner.getSelectedItem().toString();

        final String admin_id = my_dict.get(text.toLowerCase());
        Log.e("sgdsfsdf",":"+text);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePharmacy.this);
        builder.setTitle("ALERT");
        builder.setMessage("Confirm The Order Or Cancel It If You Want .... You Can Open Chat With Us In WhatsApp  ");
        //confirm button
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendRequest();
                Toast.makeText(ChoosePharmacy.this, "yes Order"+admin_id, Toast.LENGTH_LONG).show();
            }
        });
        // cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ChoosePharmacy.this, "Cancel Order", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });

        //chat button
        builder.setNeutralButton("Contact Us ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String num = "01273384570";
                Uri uri = Uri.parse("smsto:" + num);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void SendRequest() {
        RequestRef.child(senderUserId).child("tkgTYkA0z9aE4Nz7Bz5AnuWg9pf2")
                .child("request type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        RequestRef.child("tkgTYkA0z9aE4Nz7Bz5AnuWg9pf2").child(senderUserId)
                                .child("request type").setValue("Received");
                    }
                    }
                });


    }


}