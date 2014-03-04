package com.example.gginventory;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SalesScreen extends Activity {
	
	private EditText textField;
	private ListView plantList;
	private RecordDataSource datasource;
	private ArrayList<String> matchList;
	private ImageView plantPicture;
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.sales_screen);
    	initLayout();
    	initAddListeners();
    }
    
	protected void initLayout(){
		textField = (EditText)findViewById(R.id.salesEditText);
		plantList = (ListView)findViewById(R.id.salesPlantList);
		plantPicture = (ImageView)findViewById(R.id.salesPlantPicture);
        datasource = new RecordDataSource(this);
        datasource.open();
        matchList = new ArrayList<String>();
        plantList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, matchList));
	}
	
	/*set it up so every time you type a character it updates the plantList
	 * so you see it in real time. Hopefuly the device can keep up. SQL is 
	 * pretty good about it, so I'm not too worried. 
	 */
	protected void initAddListeners() {
		textField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				Cursor c;
				if(s.toString().equals(""))
					c = datasource.queryDatabase("SELECT * FROM " + MySQLiteHelper.TABLE_NAME + ";");
				else
					c = datasource.queryDatabase("SELECT * FROM " + MySQLiteHelper.TABLE_NAME + " where " + MySQLiteHelper.COLUMNS[0] + " LIKE '%" + s.toString() + "%';");
				matchList.clear();
				if(c != null){
					if(c.moveToFirst()){
						do{
							matchList.add(c.getString(c.getColumnIndex(MySQLiteHelper.COLUMNS[0])));
						} while (c.moveToNext());
					}
				}
				((BaseAdapter)plantList.getAdapter()).notifyDataSetChanged();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				return;
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				return;
			}
		});	
		
		plantList.setOnItemClickListener(new OnItemClickListener(){

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String item = ((TextView)view).getText().toString();
				
				if(plantPicture == null){
					Toast.makeText(getBaseContext(), "plant pic is null", Toast.LENGTH_LONG).show();
				}
				
				plantPicture.setImageURI(
						Uri.fromFile(
								new File(
										Environment.getExternalStoragePublicDirectory(
												Environment.DIRECTORY_PICTURES), 
												"GrowingGrounds"+ 
												File.separator + 
												item + 
												File.separator + 
												"1.jpg")));
				
			}
			
		});

	}
	
}
