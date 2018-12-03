package de.ncdf.models;

import java.sql.Timestamp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Bean for RFID-Tags
 * valid format -> 00:00:00:00:00 - ff:ff:ff:ff:ff
 * [byte4]:[byte3]:[byte2]:[byte1]:[byte0]
 * @author ChristianGoertz
 *
 */
public class RFIDTag {
	
	private IntegerProperty id_val = new SimpleIntegerProperty();
	private StringProperty tagString = new SimpleStringProperty(); 
	private StringProperty statusString = new SimpleStringProperty();
	private StringProperty timeStampString = new SimpleStringProperty();
	
	public RFIDTag() {
		super();
	}
	public RFIDTag(int id, String tag, String status, String timestamp) {
		this.id_val.set(id);
		this.tagString.set(tag);
		this.statusString.set(status);
		this.timeStampString.set(timestamp);
	}
	
	public int getIdValue() {
		return this.id_val.get();
	}
	public void setIdValu(int id) {
		this.id_val.set(id);
	}
	public IntegerProperty idValue() {
		return this.id_val;
	}
	
	public String getTagString() {
		return this.tagString.get();
	}
	public void setTagString(String s) {
		this.tagString.set(s);
	}
	public StringProperty TagString() {
		return this.tagString;
	}
	public String getStatusString() {
		return this.statusString.get();
	}
	public void setStatusString(String s) {
		this.statusString.set(s);
	}
	public StringProperty statusString() {
		return this.statusString;
	}
	public String getTimeStampString() {
		return this.timeStampString.get();
	}
	public void setTimeStampString(String s) {
		this.timeStampString.set(s);
	}
	public void setTimeStampString(Timestamp ts) {
		this.timeStampString.set(ts.toString());
	}
	public StringProperty TimeStampString() {
		return this.timeStampString;
	}
	public Timestamp getTimeStamp() {
		Timestamp ts = Timestamp.valueOf(this.timeStampString.get());
		return ts;
	}
}
