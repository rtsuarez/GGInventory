package com.example.gginventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ryan on 1/20/14.
 */
public class NewRecordScreen extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record_screen);

        Button btnNewRec = (Button) findViewById(R.id.addRecordButton);
        Button btnPrintQR = (Button) findViewById(R.id.QRButton);
        Button btnTakePhoto = (Button) findViewById(R.id.takePictureButton);

        Intent i = getIntent();

        // Binding Click event to Button
        btnNewRec.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                //Intent nextScreen = new Intent(getApplicationContext(), InventoryScreen.class);
                //Sending data to another Activity
                //startActivity(nextScreen);
                finish();
            }
        });

        btnPrintQR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                //Intent nextScreen = new Intent(getApplicationContext(), InventoryScreen.class);
                //Sending data to another Activity
                //startActivity(nextScreen);
                finish();
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener(){

            public void onClick(View argo0){

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
                //finish();
            }
        });
    }
}
