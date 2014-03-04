package com.example.gginventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.database.Cursor;

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

        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        final List<Record> records = datasource.getAllRecord();
        String[] plants = new String[records.size()];
        for (int i = 0; i < records.size(); i++)
            plants[i] = records.get(i).getName();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, plants);
        actv.setAdapter(adapter);

        actv.setThreshold(1);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String plantname = ((TextView) findViewById(R.id.autoCompleteTextView1)).getText().toString();

                Record record = new Record();

                for (int j = 0; j < records.size(); j++)
                    if (records.get(j).getName().equals(plantname)) {
                        record = records.get(j);
                        break;
                    }

                String[] qtyStrings = getResources().getStringArray(R.array.quantity);
                int k = 0;
                for (k = 0; k < qtyStrings.length; k++)
                    if (qtyStrings[k].equals(Integer.toString(record.getQty())))
                        break;

                Spinner spin = (Spinner)findViewById(R.id.ScanQtySpinner);
                spin.setSelection(k);


                String[] typeStrings = getResources().getStringArray((R.array.type));
                for (k = 0; k < typeStrings.length; k++)
                    if (typeStrings[k].equals(record.getType()))
                        break;
                Spinner spin1 = (Spinner)findViewById(R.id.ScanTypeSpinner);
                spin1.setSelection(k);

                String[] notesStrings = getResources().getStringArray(R.array.notes);
                for (k = 0; k < notesStrings.length - 1; k++)
                    if (notesStrings[k].equals(record.getNotes()))
                        break;
                Spinner spin2 = (Spinner)findViewById(R.id.ScanNotesSpinner);
                spin2.setSelection(k);

                String[] detailsStrings = getResources().getStringArray(R.array.discription);
                for (k = 0; k < detailsStrings.length - 1; k++)
                    if (detailsStrings[k].equals(record.getDetails()))
                        break;
                Spinner spin3 = (Spinner)findViewById(R.id.ScanDiscriptionSpinner);
                spin3.setSelection(k);

            }
        });

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
                
                Spinner spin4 = (Spinner)findViewById(R.id.ScanRetailSpinner);
                int retail = Integer.parseInt(spin4.getSelectedItem().toString());
                
                Spinner spin5 = (Spinner)findViewById(R.id.ScanLandscapeSpinner);
                int landscape = Integer.parseInt(spin5.getSelectedItem().toString());

                datasource.updateRecordByName(plantname, qty, type, notes, details, retail, landscape);

                finish();
            }
        });
    }
}
