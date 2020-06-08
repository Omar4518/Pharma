package com.example.pharma_user.Medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharma_user.PrescriptionDetails;
import com.example.pharma_user.R;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class DrugsActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;

    List<String> selectedItems = new ArrayList<>();
    Button confirmButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_drugs );


        listView = (ListView) findViewById(R.id.list);
        listView.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );  //**************************************************************
        EditText filterEditText = (EditText) findViewById( R.id.searchFilter);


        final List<String> drugsList = new ArrayList<>();
        drugsList.add( "Viagra" );
        drugsList.add( "Wellbutrin" );
        drugsList.add( "Metformin" );
        drugsList.add( "Ibuprofen" );
        drugsList.add( "Lorazepam" );
        drugsList.add( "Panadol" );
        drugsList.add( "Basaglar" );
        drugsList.add( "Ativan" );
        drugsList.add( "Baclofen" );
        drugsList.add( "Bydureon" );
        drugsList.add( "Zantac" );
        drugsList.add( "Paroxetine" );
        drugsList.add( "Pradaxa" );
        drugsList.add( "Yervoy" );
        drugsList.add( "Yutiq" );
        drugsList.add( "Ganciclovir" );
        drugsList.add( "Glucagon" );
        drugsList.add( "Norco" );
        drugsList.add( "Norethindrone" );
        drugsList.add( "Nuvigil" );
        drugsList.add( "Prilosec" );
        drugsList.add( "ProAir HFA" );
        drugsList.add( "Witch Hazel" );
        drugsList.add( "Wixela Inhub" );
        drugsList.add( "Welchol" );
        drugsList.add( "Xolair" );
        drugsList.add( "Xulane" );
        drugsList.add( "Vitamin A" );
        drugsList.add( "Vitamin D" );
        drugsList.add( "Valsartan" );

         adapter = new ArrayAdapter<>(this,R.layout.row_layout , R.id.txt_lan , drugsList);
        listView.setAdapter( adapter );


        filterEditText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (DrugsActivity.this).adapter.getFilter().filter( s );

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );


        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // Toast.makeText( DrugsActivity.this , drugsList.get( position ).toString(), Toast.LENGTH_SHORT).show();
              //  Intent intent = new Intent( DrugsActivity.this  , PrescriptionDetails.class );
             //   intent.putExtra( "data" , drugsList.get( position ).toString());
             //   startActivity( intent );
                String selectedItem = ((TextView) view) .getText().toString();
                if (selectedItems.contains( selectedItem ))
                {
                    selectedItems.remove( selectedItem );

                }
                else
                {
                    selectedItems.add( selectedItem );

                }

            }
        } );

        confirmButton = (Button) findViewById( R.id.btn );
        confirmButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSelectedItems();
            }
        } );

    }


    public void showSelectedItems()
    {
        String items = "";
        for (String item:selectedItems)
        {
            items+="-"+item+"\n";

        }
        Intent intent = new Intent( DrugsActivity.this  , PrescriptionDetails.class );
        intent.putExtra( "data" , items);
        startActivity( intent );

    }

}
