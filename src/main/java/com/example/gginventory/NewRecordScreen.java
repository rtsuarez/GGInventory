package com.example.gginventory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.ListIterator;

import javax.sql.CommonDataSource;

/**
 * Created by Ryan on 1/20/14.
 */
public class NewRecordScreen extends Activity{
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private ImageView mImageView;
    private RecordDataSource datasource;

    protected void onDestroy() {
        super.onDestroy();
        datasource.close();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record_screen);

        datasource = new RecordDataSource(this);
        datasource.open();

        Button btnNewRec = (Button) findViewById(R.id.addRecordButton);
        Button btnPrintQR = (Button) findViewById(R.id.QRButton);
        Button btnTakePhoto = (Button) findViewById(R.id.takePictureButton);

        mImageView = (ImageView) findViewById(R.id.imageView1);
        
        Intent i = getIntent();

        // Binding Click event to Button
        btnNewRec.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                //Intent nextScreen = new Intent(getApplicationContext(), InventoryScreen.class);
                //Sending data to another Activity
                //startActivity(nextScreen);
                Record newRec = new Record();
                TextView tv = (TextView) findViewById(R.id.autoCompleteTextView1);
                newRec.setName(tv.getText().toString());
                
                Spinner spin = (Spinner)findViewById(R.id.NRQtySpinner);
                int qty = Integer.parseInt(spin.getSelectedItem().toString());
                newRec.setQty(qty);

                Spinner spin1 = (Spinner)findViewById(R.id.NRTypeSpinner);
                String type = spin1.getSelectedItem().toString();
                newRec.setType(type);

                Spinner spin2 = (Spinner)findViewById(R.id.NRNotesSpinner);
                String notes = spin2.getSelectedItem().toString();
                newRec.setNotes(notes);

                Spinner spin3 = (Spinner)findViewById(R.id.NRDiscriptionSpinner);
                String details = spin3.getSelectedItem().toString();
                newRec.setDetails(details);
                
                //newRec.defaultFields();
                Record r = datasource.createRecord(newRec);
                finish();
            }
        });

        btnPrintQR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                //Intent nextScreen = new Intent(getApplicationContext(), InventoryScreen.class);
                //Sending data to another Activity
                //startActivity(nextScreen);
            	//DatabaseSync dbSync = new DatabaseSync();
            	//dbSync.execute(getApplicationContext());

                GGDataSync dbSync = new GGDataSync();
                dbSync.execute(getApplicationContext());

                finish();
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener(){

            public void onClick(View argo0){
            	dispatchTakePictureIntent();
                //finish();
            }
        });
    }
    
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
