package com.example.pharma_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrescriptionDetails extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_prescription_details );



        textView = (TextView) findViewById( R.id.prescription_content);
        Bundle extras = getIntent().getExtras();
        String data= extras.getString( "data" );

        textView.setText( data );





    }
}
