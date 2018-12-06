package de.ncdf.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private IntegerProperty dbId = new SimpleIntegerProperty();
	private StringProperty rfidString = new SimpleStringProperty();
	private StringProperty role = new SimpleStringProperty();
	private StringProperty vorname = new SimpleStringProperty();
	private StringProperty nachname = new SimpleStringProperty();
	private StringProperty geschlecht = new SimpleStringProperty();
	private StringProperty strasse = new SimpleStringProperty();
	private StringProperty wohnort = new SimpleStringProperty();
	private IntegerProperty postleitzahl = new SimpleIntegerProperty();
	private StringProperty telefonnummer = new SimpleStringProperty();
	private StringProperty emailadresse = new SimpleStringProperty();
	private ObjectProperty<LocalDate> geburtstag = new SimpleObjectProperty<LocalDate>();
	
	public void setDbId(int i) {
		this.dbId.set(i);
	}
	public int getDbId() {
		return this.dbId.get();
	}
	public IntegerProperty DbId() {
		return this.dbId;
	}
	public void setRfidString(String s) {
		this.rfidString.set(s);
	}
	public String getRfidString() {
		return this.rfidString.get();
	}
	public StringProperty RfidString() {
		return this.rfidString;
	}
	public void setRole(String s) {
		this.role.set(s);
	}
	public String getRole() {
		return this.role.get();
	}
	public StringProperty Role() {
		return this.role;
	}
	public void setVorname(String vn) {
		this.vorname.set(vn);
	}
	public String getVorname() {
		return this.vorname.get();
	}
	public StringProperty Vorname() {
		return this.vorname;
	}
	
	public void setNachname(String nn) {
		this.nachname.set(nn);
	}
	public String getNachname() {
		return this.nachname.get();
	}
	public StringProperty Nachname() {
		return this.nachname;
	}
	
	public void setGeschlecht(String g) {
		this.geschlecht.set(g);
	}
	public String getGeschlecht() {
		return this.geschlecht.get();
	}
	public StringProperty Geschlecht() {
		return this.geschlecht;
	}
	public void setStrasse(String s) {
		this.strasse.set(s);
	}
	public String getStrasse() {
		return this.strasse.get();
	}
	public void setWohnort(String s) {
		this.wohnort.set(s);
	}
	public String getWohnort() {
		return this.wohnort.get();
	}
	public StringProperty Wohnort() {
		return this.wohnort;
	}
	public void setPostleitzahl(int i) {
		this.postleitzahl.set(i);
	}
	public int getPostleitzahl() {
		return this.postleitzahl.get();
	}
	public IntegerProperty Postleitzahl() {
		return this.postleitzahl;
	}
	public void setTelefonnummer(String s) {
		this.telefonnummer.set(s);
	}
	public String getTelefonnummer() {
		return this.telefonnummer.get();
	}
	public StringProperty Telefonnummer() {
		return this.telefonnummer;
	}
	public void setEmailadresse(String s) {
		this.emailadresse.set(s);
	}
	public String getEmailadresse() {
		return this.emailadresse.get();
	}
	public StringProperty Emailadresse() {
		return this.emailadresse;
	}
	public void setGeburtstag(LocalDate d) {
		this.geburtstag.set(d);
	}
	public LocalDate getGeburtstag() {
		return this.geburtstag.get();
	}
	public ObjectProperty<LocalDate> Geburtstag(){
		return this.geburtstag;
	}
	
}
