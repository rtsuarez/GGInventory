package com.example.gginventory;

/**
 * Created by Ryan on 1/29/14.
 */
public class Record {
	private String plantName;
	private String commonName;
	private String description;
	private String type;
	private String notes;
	private String details;
	private int qty;
	private int retail;
	private int landscape;
	private String USDA_Zone;

	public Record(){
	}

	public String getName(){
		return this.plantName;
	}
	
	public String getCommonName(){
		return this.commonName;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getNotes(){
		return this.notes;
	}
	
	public String getDetails(){
		return this.details;
	}
	
	public int getQty(){
		return this.qty;
	}
	
	public int getRetail(){
		return this.retail;
	}
	
	public int getLandscape(){
		return this.landscape;
	}
	
	public String getUSDA_Zone(){
		return this.USDA_Zone;
}
	
	public void setName(String plantName){
		this.plantName = plantName;
	}

	public void setCommonName(String commonName){
		this.commonName = commonName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public void setType(String type){
		this.type = type;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public void setQty(int qty){
		this.qty = qty;
	}

	public void setRetail(int retail){
		this.retail = retail;
	}

	public void setLandscape(int landscape){
		this.landscape = landscape;
	}

	public void setUSDA_Zone(String USDA_Zone){
		this.USDA_Zone = USDA_Zone;
	}


    public void defaultFields() {
        this.qty = 1;
        this.type = "type";
        this.notes = "notes";
        this.details = "details";
    }

    public void setRecord(Record r) {
        this.plantName = r.plantName;
        this.commonName = r.commonName;
        this.description = r.description;
        this.qty = r.qty;
        this.type = r.type;
        this.notes = r.notes;
        this.details = r.details;
        this.retail = r.retail;
        this.landscape = r.landscape;
        this.USDA_Zone = r.USDA_Zone;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return this.plantName;
    }
} 