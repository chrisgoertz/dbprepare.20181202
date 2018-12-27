package de.ncdf.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Anwesenheit {
	StringProperty nachname = new SimpleStringProperty();
	StringProperty vorname = new SimpleStringProperty();
	StringProperty kommt = new SimpleStringProperty();
	StringProperty geht = new SimpleStringProperty();
	StringProperty konto = new SimpleStringProperty();
	
	public void setNachname(String s) {
		this.nachname.set(s);
		
	}
	public String getNachname() {
		return this.nachname.get();
	}
	public StringProperty Nachname() {
		return this.nachname;
	}
	public void setVorname(String s) {
		this.vorname.set(s);
	}
	public String getVorname() {
		return this.vorname.get();
	}
	public StringProperty Vorname() {
		return this.vorname;
	}
	public void setKommt(String s) {
		this.kommt.set(s);
	}
	public String getKommt() {
		return this.kommt.get();
	}
	public StringProperty Kommt() {
		return this.kommt;
	}
	public void setGeht(String s) {
		this.geht.set(s);
	}
	public String getGeht() {
		return this.geht.get();
	}
	public StringProperty Geht() {
		return this.geht;
	}
	public void setKonto(String s) {
		this.konto.set(s);
	}
	public String getKonto() {
		return this.konto.get();
	}
	public StringProperty Konto() {
		return this.konto;
	}
}
