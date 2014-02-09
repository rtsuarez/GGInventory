package com.example.gginventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ryan on 1/20/14.
 */
public class ScanItemScreen extends Activity{

    private RecordDataSource datasource;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_item_screen);

        datasource = new RecordDataSource(this);
        datasource.open();

        Button btnUpdate = (Button) findViewById(R.id.updateButton);

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        List<Record> records = datasource.getAllRecord();
        String[] plants = new String[records.size()];
        for (int i = 0; i < records.size(); i++)
            plants[i] = records.get(i).getName();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, plants);
        actv.setAdapter(adapter);

        Intent i = getIntent();

        // Binding Click event to Button
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                //Intent nextScreen = new Intent(getApplicationContext(), InventoryScreen.class);
                //Sending data to another Activity
                //startActivity(nextScreen);

                Record newRec = new Record();
                String plantname = ((TextView) findViewById(R.id.autoCompleteTextView1)).getText().toString();

                Spinner spin = (Spinner)findViewById(R.id.ScanQtySpinner);
                int qty = Integer.parseInt(spin.getSelectedItem().toString());

                Spinner spin1 = (Spinner)findViewById(R.id.ScanTypeSpinner);
                String type = spin1.getSelectedItem().toString();

                Spinner spin2 = (Spinner)findViewById(R.id.ScanNotesSpinner);
                String notes = spin2.getSelectedItem().toString();

                Spinner spin3 = (Spinner)findViewById(R.id.ScanDiscriptionSpinner);
                String details = spin3.getSelectedItem().toString();

                datasource.updateRecordByName(plantname, qty, type, notes, details);

                finish();
            }
        });
    }
}
