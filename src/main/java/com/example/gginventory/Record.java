package com.example.gginventory;

/**
 * Created by Ryan on 1/29/14.
 */
public class Record {
    private String name;
    private int qty;
    private String type;
    private String notes;
    private String details;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Record getRecord() {
        return this;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) { this.qty = qty; }

    public void defaultFields() {
        this.qty = 1;
        this.type = "type";
        this.notes = "notes";
        this.details = "details";
    }

    public void setRecord(Record r) {
        this.name = r.name;
        this.qty = r.qty;
        this.type = r.type;
        this.notes = r.notes;
        this.details = r.details;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return this.name;
    }
} 