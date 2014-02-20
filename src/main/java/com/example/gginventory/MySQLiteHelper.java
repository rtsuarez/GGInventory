package com.example.gginventory;

/**
 * Created by Ryan on 1/29/14.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "inventory";
    public static final String[] COLUMNS = {"_plantname", "_commonname", "_description", "_type", "_notes", "_details", "_qty", "_retail", "_landscape", "_usdazone"};

    private static final String DATABASE_NAME = "gginventory.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    /*1000 characters may seem like a bit much, but the official records atm have some really
     * shoddy names, one of which is ~600 chars long. So, better safe...
     */
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMNS[0] + " VARCHAR(1000) PRIMARY KEY, "
            + COLUMNS[1] + " VARCHAR(1000), "
            + COLUMNS[2] + " VARCHAR(1000), "
            + COLUMNS[3] + " VARCHAR(10), "
            + COLUMNS[4] + " VARCHAR(10), "
            + COLUMNS[5] + " VARCHAR(10), "
            + COLUMNS[6] + " INTEGER, "
            + COLUMNS[7] + " INTEGER, "
            + COLUMNS[8] + " INTEGER, "
            + COLUMNS[9] + " VARCHAR(10)"
            + ");";


    public void updateRowByName(String plantname, int qty, String type, String notes, String details, int retail, int landscape) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put("_qty", qty);
        args.put("_type", type);
        args.put("_notes", notes);
        args.put("_details", details);
        args.put("_retail", retail);
        args.put("_landscape", landscape);

        db.update(TABLE_NAME, args, "_plantname=\"" + plantname + "\"", null);
    }

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    	Log.w("MySQLiteHelper", DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}