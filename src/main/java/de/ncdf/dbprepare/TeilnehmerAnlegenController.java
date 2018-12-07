package de.ncdf.dbprepare;

import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.TeilnehmerDB;
import de.ncdf.models.Teilnehmer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TeilnehmerAnlegenController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField tfDatenbankId;

	@FXML
	private TextField tfRfid;

	@FXML
	private TextField tfEmailadresse;

	@FXML
	private TextField tfNachname;

	@FXML
	private Button btnSpeichern;

	@FXML
	private TextField tfStrasse;

	@FXML
	private TextField tfVorname;

	@FXML
	private DatePicker dpGeburtstag;

	@FXML
	private DatePicker dpEintrittsdatum;

	@FXML
	private TextField tfPostleitzahl;

	@FXML
	private TextField tfTelefonnummer;

	@FXML
	private ComboBox<String> cbGeschlecht;
	@FXML
	private ComboBox<String> cbRole;

	@FXML
	private TextField tfWohnort;

	@FXML
	void btnSpeichernClicked(ActionEvent event) {
		Teilnehmer t = new Teilnehmer();
		t.setRfidString(tfRfid.getText());
		t.setVorname(tfVorname.getText());
		t.setNachname(tfNachname.getText());
		t.setGeschlecht(cbGeschlecht.getSelectionModel().getSelectedItem());
		t.setStrasse(tfStrasse.getText());
		t.setWohnort(tfWohnort.getText());
		t.setPostleitzahl(Integer.parseInt(tfPostleitzahl.getText()));
		t.setTelefonnummer(tfTelefonnummer.getText());
		t.setEmailadresse(tfEmailadresse.getText());
		t.setGeburtstag(dpGeburtstag.getValue());
		t.setEintrittsdatum(dpEintrittsdatum.getValue());
		t.setRole(cbRole.getSelectionModel().getSelectedItem());
		TeilnehmerDB.insert(t);
		clearForm();
	}

	@FXML
	void initialize() {
		assert tfDatenbankId != null : "fx:id=\"tfDatenbankId\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfEmailadresse != null : "fx:id=\"tfEmailadresse\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfNachname != null : "fx:id=\"tfNachname\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert btnSpeichern != null : "fx:id=\"btnSpeichern\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfStrasse != null : "fx:id=\"tfStrasse\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfVorname != null : "fx:id=\"tfVorname\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert dpGeburtstag != null : "fx:id=\"dpGeburtstag\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert dpEintrittsdatum != null : "fx:id=\"dpEintrittsdatum\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfPostleitzahl != null : "fx:id=\"tfPostleitzahl\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfTelefonnummer != null : "fx:id=\"tfTelefonnummer\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert cbGeschlecht != null : "fx:id=\"cbGeschlecht\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		assert tfWohnort != null : "fx:id=\"tfWohnort\" was not injected: check your FXML file 'TeilnehmerAnlegen.fxml'.";
		initTfVorname();
		initTfNachname();
		initComboBox();
		initTfWohnort();
		initTfPostleitzahl();
		initTfRfid();
		initTfEmailadresse();
		initTfTelefonnummer();
	}

	private void initComboBox() {
		ObservableList<String> cbListSex = FXCollections.observableArrayList();
		ObservableList<String> cbListRole = FXCollections.observableArrayList();
		cbListSex.add("männlich");
		cbListSex.add("weiblich");
		cbListSex.add("undefined");
		cbGeschlecht.setItems(cbListSex);
		cbListRole.add("Admin");
		cbListRole.add("Umschüler");
		cbListRole.add("Azubi");
		cbListRole.add("Praktikant");
		cbRole.setItems(cbListRole);
	}

	private void initTfPostleitzahl() {
		tfPostleitzahl.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,5}")) { 
					tfPostleitzahl.setText(oldValue);
				}
			}
		});
	}

	private void initTfRfid() {
		tfRfid.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tfRfid.setText(tfRfid.getText().toUpperCase());
				if (!newValue.matches("([A-F\\d]{2}:){4}[A-F\\d]{2}")) { 
					
					tfRfid.setStyle("-fx-text-inner-color: red");
					//TODO: must be fixed! disable the button here is bullshit...
					//just for testing purpose...
					btnSpeichern.setDisable(true);
				}
				else {
					tfRfid.setStyle("-fx-text-inner-color: black");
					btnSpeichern.setDisable(false);
				}
			}
		});
	}
	
	private void initTfWohnort() {
		tfWohnort.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[A-Z][a-z\\D]{0,35}")) { 
					
					tfWohnort.setStyle("-fx-text-inner-color: red");
					
				}
				else {
					tfWohnort.setStyle("-fx-text-inner-color: black");
					
				}
			}
		});
	}
	private void initTfVorname() {
		tfVorname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[A-Z][a-z\\D]{0,35}")) { 
					
					tfVorname.setStyle("-fx-text-inner-color: red");
					
				}
				else {
					tfVorname.setStyle("-fx-text-inner-color: black");
					
				}
			}
		});
	}
	private void initTfNachname() {
		tfVorname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[A-Z][a-z\\D]{0,35}")) { 
					
					tfNachname.setStyle("-fx-text-inner-color: red");
					
				}
				else {
					tfNachname.setStyle("-fx-text-inner-color: black");
					
				}
			}
		});
	}
	private void initTfEmailadresse() {
		tfEmailadresse.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^(.+)@(.+)$")) { 
					
					tfEmailadresse.setStyle("-fx-text-inner-color: red");
					
				}
				else {
					tfEmailadresse.setStyle("-fx-text-inner-color: black");
					
				}
			}
		});
	}
	private void initTfTelefonnummer() {
		tfTelefonnummer.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,25}")) { 
					tfTelefonnummer.setText(oldValue);
					
				}
				
			}
		});
	}
	/**
	 * clear all textfields, comboboxes and datepickers
	 */
	private void clearForm() {
		this.tfVorname.setText("");
		this.tfNachname.setText("");
		this.tfRfid.setText("");
		this.tfEmailadresse.setText("");
		this.tfTelefonnummer.setText("");
		this.tfStrasse.setText("");
		this.tfWohnort.setText("");
		this.tfPostleitzahl.setText("");
		this.cbRole.getSelectionModel().select(0);
		this.cbGeschlecht.getSelectionModel().select(0);
		this.dpEintrittsdatum.setValue(null);
		this.dpGeburtstag.setValue(null);
		System.out.println("form cleared");
	}
	private boolean isFormValid() {
		boolean retval = false;
		//TODO:user hashmap to store bool (value OK) for every form field
		//iterate over hashmap
		//if all form fields are valid, enable save button
		return retval;
	}
}
