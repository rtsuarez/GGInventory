package com.example.gginventory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.util.List;
import java.util.ListIterator;

import javax.sql.CommonDataSource;

/**
 * Created by Ryan on 1/20/14.
 */
public class NewRecordScreen extends Activity{
	static final int PICTURE_REQUEST_CODE = 1;
	
	private ImageView mImageView;
    private RecordDataSource datasource;
    protected void onDestroy() {
        super.onDestroy();
        datasource.close();
    }

    private Button btnNewRec;
    private Button btnPrimaryPhoto;
    private Button btnSecondaryPhoto;
    private TextView plantName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record_screen);

        datasource = new RecordDataSource(this);
        datasource.open();

        btnNewRec = (Button) findViewById(R.id.addRecordButton);
        btnPrimaryPhoto = (Button) findViewById(R.id.NewRecordPrimaryPicture);
        btnSecondaryPhoto = (Button) findViewById(R.id.NewRecordSecondaryPicture);
        plantName = (TextView) findViewById(R.id.autoCompleteTextView1);
        
        mImageView = (ImageView) findViewById(R.id.imageView1);
        
        setupListeners();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == PICTURE_REQUEST_CODE){
            if(RESULT_OK == resultCode){
                Toast.makeText(getBaseContext(), R.string.pictureSuccess, Toast.LENGTH_LONG).show();
            } else if(RESULT_CANCELED == resultCode){
       		 Toast.makeText(getBaseContext(), R.string.pictureFail, Toast.LENGTH_LONG).show();
       	 }
        }
    }

    private void setupListeners(){
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
                
                EditText spin = (EditText)findViewById(R.id.NRQtySpinner);
                int qty = Integer.parseInt(spin.getText().toString());
                newRec.setQty(qty);

                EditText spin1 = (EditText)findViewById(R.id.NRTypeSpinner);
                String type = spin1.getText().toString();
                newRec.setType(type);

                EditText spin2 = (EditText)findViewById(R.id.NRNotesSpinner);
                String notes = spin2.getText().toString();
                newRec.setNotes(notes);

                EditText spin3 = (EditText)findViewById(R.id.NRDiscriptionSpinner);
                String details = spin3.getText().toString();
                newRec.setDetails(details);

                datasource.createRecord(newRec);
                finish();
            }
        });

        btnPrimaryPhoto.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View argo0){
            	if(plantName.getText().toString().equals("")){
            		Toast.makeText(getBaseContext(), "Must fill out plant name before taking picture", Toast.LENGTH_LONG).show();
            		return;
            	}
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	Uri uri = PictureHelper.getOutputMediaFileUri(PICTURE_REQUEST_CODE, 1, plantName.getText().toString());
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	        	startActivityForResult(intent, PICTURE_REQUEST_CODE);
            }
        });

        btnSecondaryPhoto.setOnClickListener(new View.OnClickListener(){

            public void onClick(View argo0){
            	if(plantName.getText().toString().equals("")){
            		Toast.makeText(getBaseContext(), "Must fill out plant name before taking picture", Toast.LENGTH_LONG).show();
            		return;
            	}
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	Uri uri = PictureHelper.getOutputMediaFileUri(PICTURE_REQUEST_CODE, 2, plantName.getText().toString());
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	        	startActivityForResult(intent, PICTURE_REQUEST_CODE);
            }
        });
    }
}
