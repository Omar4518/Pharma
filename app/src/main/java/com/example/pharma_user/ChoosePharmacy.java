package com.example.pharma_user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_user.R;

public class ChoosePharmacy extends AppCompatActivity {
    private Button submitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pharmacy);
        TextView textView = findViewById(R.id.text_view_number_pharma);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ChoosePharmacy.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePharmacy.this);
        builder.setTitle("ALERT");
        builder.setMessage("Confirm The Order Or Cancel It If You Want .... You Can Open Chat With Us In WhatsApp  ");
        //confirm button
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ChoosePharmacy.this, "yes Order", Toast.LENGTH_LONG).show();
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
}