package de.ncdf.dbprepare;

import java.net.URL;
import java.util.ResourceBundle;

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
	private TextField tfWohnort;

	@FXML
	void btnSpeichernClicked(ActionEvent event) {
		System.out.println("Speichern geklickt");
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
	}

	private void initComboBox() {
		ObservableList<String> cbList = FXCollections.observableArrayList();
		cbList.add("männlich");
		cbList.add("weiblich");
		cbList.add("undefined");
		cbGeschlecht.setItems(cbList);
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
}
