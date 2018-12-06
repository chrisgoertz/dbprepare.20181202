package de.ncdf.models;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Teilnehmer extends Person {
	private ObjectProperty<LocalDate> eintrittsdatum = new SimpleObjectProperty<LocalDate>();
	
	public void setEintrittsdatum(LocalDate d) {
		this.eintrittsdatum.set(d);
	}
	public LocalDate getEintrittsdatum() {
		return this.eintrittsdatum.get();
	}
	public ObjectProperty<LocalDate> Eintrittsdatum(){
		return this.eintrittsdatum;
	}
}
