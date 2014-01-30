package com.example.gginventory;

/**
 * Created by Ryan on 1/29/14.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "inventory";
    public static final String[] COLUMNS = {"_plantname", "_qty", "_type", "_notes", "_details"};

    private static final String DATABASE_NAME = "gginventory.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMNS[0] + " VARCHAR(50) PRIMARY KEY, "
            + COLUMNS[1] + " INTEGER, "
            + COLUMNS[2] + " INTEGER, "
            + COLUMNS[3] + " INTEGER, "
            + COLUMNS[4] + " INTEGER"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
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

}