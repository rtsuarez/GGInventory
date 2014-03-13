package com.example.gginventory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Ryan on 1/20/14.
 */
public class InventoryScreen extends Activity {
    /** Called when the activity is first created. */
    private RecordDataSource datasource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_screen2);

        datasource = new RecordDataSource(this);
        datasource.open();

        final LinearLayout lView = new LinearLayout(this);
        final TextView myText = new TextView(this);

        Button btnScan = (Button) findViewById(R.id.scan_item_button);
        Button btnNewRec = (Button) findViewById(R.id.new_record_button);
        Button btnTabToPc = (Button) findViewById(R.id.sync_to_computer);
        Button btnPcToTab = (Button) findViewById(R.id.sync_from_computer);

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

        btnTabToPc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                GGDataSync dbSync = new GGDataSync();
                dbSync.execute(getApplicationContext());

            }

        });

        btnPcToTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                DatabaseSync dbSync = new DatabaseSync();
                dbSync.execute(getApplicationContext());

            }

        });

    }
}
