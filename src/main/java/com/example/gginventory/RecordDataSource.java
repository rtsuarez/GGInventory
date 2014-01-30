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
        System.out.println("QTY = " + record.getQty());
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

    private Record cursorToRecord(Cursor cursor) {
        Record record = new Record();
        record.setName(cursor.getString(0));
        record.setQty(Integer.parseInt(cursor.getString(1)));
        return record;
    }
} 