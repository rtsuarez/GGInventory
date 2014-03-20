package com.example.gginventory;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Ryan on 1/20/14.
 */
public class ScanItemScreen extends Activity{
	
	static final int PICTURE_REQUEST_CODE = 1;

    private RecordDataSource datasource;
    private Button btnSecondaryPhoto, btnPrimaryPhoto;
    private String plantName;
    private Boolean inputError = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_item_screen);
        btnPrimaryPhoto = (Button) findViewById(R.id.ScanItemPrimaryPicture);
        btnSecondaryPhoto = (Button) findViewById(R.id.ScanItemSecondaryPicture);

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

                EditText spin = (EditText)findViewById(R.id.ScanQtySpinner);
                spin.setText(Integer.toString(record.getQty()), TextView.BufferType.EDITABLE);

                EditText spin1 = (EditText)findViewById(R.id.ScanTypeSpinner);
                spin1.setText(record.getType(), TextView.BufferType.EDITABLE);

                EditText spin2 = (EditText)findViewById(R.id.ScanNotesSpinner);
                spin2.setText(record.getNotes(), TextView.BufferType.EDITABLE);

                EditText spin3 = (EditText)findViewById(R.id.ScanTypeSpinner);
                spin3.setText(record.getType(), TextView.BufferType.EDITABLE);

                EditText spin4 = (EditText)findViewById(R.id.ScanRetailSpinner);
                spin4.setText(Integer.toString(record.getRetail()), TextView.BufferType.EDITABLE);

                EditText spin5 = (EditText)findViewById(R.id.ScanLandscapeSpinner);
                spin5.setText(Integer.toString(record.getLandscape()), TextView.BufferType.EDITABLE);

                EditText spin6 = (EditText)findViewById(R.id.ScanDiscriptionSpinner);
                spin6.setText(record.getDetails(), TextView.BufferType.EDITABLE);
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

                try {
                    EditText spin = (EditText)findViewById(R.id.ScanQtySpinner);
                    int qty = Integer.parseInt((spin.getText().toString()));

                    EditText spin1 = (EditText)findViewById(R.id.ScanTypeSpinner);
                    String type = spin1.getText().toString();

                    EditText spin2 = (EditText)findViewById(R.id.ScanNotesSpinner);
                    String notes = spin2.getText().toString();

                    EditText spin3 = (EditText)findViewById(R.id.ScanDiscriptionSpinner);
                    String details = spin3.getText().toString();

                    EditText spin4 = (EditText)findViewById(R.id.ScanRetailSpinner);
                    int retail = Integer.parseInt(spin4.getText().toString());

                    EditText spin5 = (EditText)findViewById(R.id.ScanLandscapeSpinner);
                    int landscape = Integer.parseInt(spin5.getText().toString());

                    datasource.updateRecordByName(plantname, qty, type, notes, details, retail, landscape);
                } catch (Exception e) {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            ScanItemScreen.this);

// Setting Dialog Title
                    alertDialog2.setTitle("Error: incorrect values entered.");

// Setting Dialog Message
                    alertDialog2.setMessage("Please check and make sure that quantity, landscape, and retail are NUMBER values");

// Setting Icon to Dialog
                    //alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                    inputError = true;
                    alertDialog2.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    inputError = true;
                                }
                            });

// Showing Alert Dialog
                    alertDialog2.show();
                }
                if (inputError == false)
                    finish();
                else
                    inputError = false;
            }
        });
        btnPrimaryPhoto.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View argo0){
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	Uri uri = PictureHelper.getOutputMediaFileUri(PICTURE_REQUEST_CODE, 1, ((TextView) findViewById(R.id.autoCompleteTextView1)).getText().toString());
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	        	startActivityForResult(intent, PICTURE_REQUEST_CODE);
            }
        });

        btnSecondaryPhoto.setOnClickListener(new View.OnClickListener(){

            public void onClick(View argo0){
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	Uri uri = PictureHelper.getOutputMediaFileUri(PICTURE_REQUEST_CODE, 2, ((TextView) findViewById(R.id.autoCompleteTextView1)).getText().toString());
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	        	startActivityForResult(intent, PICTURE_REQUEST_CODE);
            }
        });
    }
}
