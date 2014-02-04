package com.example.gginventory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by Ryan on 1/20/14.
 */
public class InventoryScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_screen2);

        Button btnScan = (Button) findViewById(R.id.scan_item_button);
        Button btnNewRec = (Button) findViewById(R.id.new_record_button);
        Button btnDumpDb = (Button) findViewById(R.id.dump_database_button);

        Intent i = getIntent();

        // Binding Click event to Button
        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), ScanItemScreen.class);
                //Sending data to another Activity
                startActivity(nextScreen);
            }
        });

        btnNewRec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), NewRecordScreen.class);
                //Sending data to another Activity
                startActivity(nextScreen);
            }
        });

        btnDumpDb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Closing SecondScreen Activity
                finish();
            }
        });

    }
}
