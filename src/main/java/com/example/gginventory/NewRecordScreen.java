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
    
    private Button btnNewRec;
    private Button btnPrintQR;
    private Button btnTakePhoto;
    private TextView plantName;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record_screen);

        datasource = new RecordDataSource(this);
        datasource.open();

        btnNewRec = (Button) findViewById(R.id.addRecordButton);
        btnPrintQR = (Button) findViewById(R.id.QRButton);
        btnTakePhoto = (Button) findViewById(R.id.takePictureButton);
        plantName = (TextView) findViewById(R.id.autoCompleteTextView1);
        
        mImageView = (ImageView) findViewById(R.id.imageView1);
        
        setupListeners();
    }
    
    @SuppressLint("NewApi") /*not sure if this is going to break all the things, but i'ma roll w/ it for now */
	private File getOutputMediaFile(int type){
    	//file for pics/growingGrounds
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GrowingGrounds" + File.separator + plantName.getText().toString());

		if(!mediaStorageDir.exists()){
			if(!(mediaStorageDir.mkdir() || mediaStorageDir.isDirectory())){
				Log.d("NewRecordScreen", "directory not created correctly");
				return null;
			}
		}
		//until i figure out how to grab the first image in a file, they are all getting called the same thing: 1.jpg
		//String filePath = mediaStorageDir.getPath() + File.separator + "IMG_" + DateFormat.getDateTimeInstance().format(System.currentTimeMillis()) + ".jpg";
		String filePath = mediaStorageDir.getPath() + File.separator + "1" + ".jpg";
		filePath.replaceAll("\\s+", "");
		if(type == PICTURE_REQUEST_CODE){
			return new File(filePath);
		}
		return null;
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

                datasource.createRecord(newRec);
                finish();
            }
        });

        btnPrintQR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                //Intent nextScreen = new Intent(getApplicationContext(), InventoryScreen.class);
                //Sending data to another Activity
                //startActivity(nextScreen);
            	DatabaseSync dbSync = new DatabaseSync();
            	dbSync.execute(getApplicationContext());

                //finish();
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener(){

            public void onClick(View argo0){
            	if(plantName.getText().toString().equals("")){
            		Toast.makeText(getBaseContext(), "Must fill out plant name before taking picture", Toast.LENGTH_LONG).show();
            		return;
            	}
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	Uri uri = getOutputMediaFileUri(PICTURE_REQUEST_CODE);
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	        	startActivityForResult(intent, PICTURE_REQUEST_CODE);
            }
        });
    }
    
	private Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}
}
