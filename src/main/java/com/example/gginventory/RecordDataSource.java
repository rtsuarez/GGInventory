package com.example.gginventory;

/**
 * Created by Ryan on 1/29/14.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RecordDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = MySQLiteHelper.COLUMNS;

    public RecordDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Record createRecord(Record record) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMNS[0], record.getName());
        values.put(MySQLiteHelper.COLUMNS[1], record.getQty());
        values.put(MySQLiteHelper.COLUMNS[2], record.getType());
        values.put(MySQLiteHelper.COLUMNS[3], record.getNotes());
        values.put(MySQLiteHelper.COLUMNS[4], record.getDetails());

        long insertName = database.insert(MySQLiteHelper.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, MySQLiteHelper.COLUMNS[0] + " = " + insertName, null,
                null, null, null);
        cursor.moveToFirst();
 //       Record newRecord = cursorToRecord(cursor);
        cursor.close();
        return record;
    }

    public void deleteRecord(Record record) {
        String name = record.getName();
        System.out.println("Record deleted with id: " + name);
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMNS[0]
                + " = " + name, null);
    }


    public void updateRecordByName(String plantname, int qty, String type, String notes, String details, int retail, int landscape) {
        dbHelper.updateRowByName(plantname, qty, type, notes, details, retail, landscape);
    }

    public List<Record> getAllRecord() {
        List<Record> records = new ArrayList<Record>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Record record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return records;
    }
    
    public Cursor queryDatabase(String query){
    	return database.rawQuery(query, null);
    }

    public Record cursorToRecord(Cursor cursor) {
        Record record = new Record();
        record.setName(cursor.getString(0));
        record.setQty(Integer.parseInt(cursor.getString(1)));
        record.setType(cursor.getString(2));
        record.setNotes(cursor.getString(3));
        record.setDetails(cursor.getString(4));
        return record;
    }
} 