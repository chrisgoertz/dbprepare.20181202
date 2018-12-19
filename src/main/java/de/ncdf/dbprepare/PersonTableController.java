package de.ncdf.dbprepare;

import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.TeilnehmerDB;
import de.ncdf.models.RFIDTag;
import de.ncdf.models.Teilnehmer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PersonTableController implements GuiPage{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Teilnehmer> teilnehmerTable;
    
    @FXML
    private TableColumn<Teilnehmer, String> columnVorname;

    @FXML
    private TableColumn<Teilnehmer, String> columnStrasse;

    @FXML
    private TableColumn<Teilnehmer, String> columnTelefonnummer;

    @FXML
    private TableColumn<Teilnehmer, String> columnEmailadresse;

    @FXML
    private TableColumn<Teilnehmer, String> columnGeburtstag;

    @FXML
    private TableColumn<Teilnehmer, String> columnWohnort;

    @FXML
    private TableColumn<Teilnehmer, String> columnNachname;

    @FXML
    private TableColumn<Teilnehmer, String> columnGeschlecht;

    @FXML
    private TableColumn<Teilnehmer, String> columnEintrittsdatum;

    @FXML
    private TableColumn<Teilnehmer, String> columnRole;

    @FXML
    private TableColumn<Teilnehmer, String> columnPostleitzahl;
    
    @FXML
    private TableColumn<Teilnehmer, String> columnRFID;

    @FXML
    private TableColumn<Teilnehmer, String> columnID;

    @FXML
    void initialize() {
        assert columnVorname != null : "fx:id=\"columnVorname\" was not injected: check your FXML file 'Untitled'.";
        assert columnStrasse != null : "fx:id=\"columnStrasse\" was not injected: check your FXML file 'Untitled'.";
        assert columnTelefonnummer != null : "fx:id=\"columnTelefonnummer\" was not injected: check your FXML file 'Untitled'.";
        assert columnEmailadresse != null : "fx:id=\"columnEmailadresse\" was not injected: check your FXML file 'Untitled'.";
        assert columnGeburtstag != null : "fx:id=\"columnGeburtstag\" was not injected: check your FXML file 'Untitled'.";
        assert columnWohnort != null : "fx:id=\"columnWohnort\" was not injected: check your FXML file 'Untitled'.";
        assert columnNachname != null : "fx:id=\"columnNachname\" was not injected: check your FXML file 'Untitled'.";
        assert columnGeschlecht != null : "fx:id=\"columnGeschlecht\" was not injected: check your FXML file 'Untitled'.";
        assert columnEintrittsdatum != null : "fx:id=\"columnEintrittsdatum\" was not injected: check your FXML file 'Untitled'.";
        assert columnRole != null : "fx:id=\"columnRole\" was not injected: check your FXML file 'Untitled'.";
        assert columnPostleitzahl != null : "fx:id=\"columnPostleitzahl\" was not injected: check your FXML file 'Untitled'.";
        assert columnRFID != null : "fx:id=\"columnRFID\" was not injected: check your FXML file 'Untitled'.";
        assert columnID != null : "fx:id=\"columnID\" was not injected: check your FXML file 'Untitled'.";
        
        initTable();
    }
    private void initTable() {
    	columnVorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
    	columnVorname.setMinWidth(70.0);
    	columnNachname.setCellValueFactory(new PropertyValueFactory<>("nachname"));
    	columnNachname.setMinWidth(70.0);
    	columnStrasse.setCellValueFactory(new PropertyValueFactory<>("strasse"));
    	columnStrasse.setMinWidth(70.0);
    	columnTelefonnummer.setCellValueFactory(new PropertyValueFactory<>("telefonnummer"));
    	columnTelefonnummer.setMinWidth(100.0);
    	columnEmailadresse.setCellValueFactory(new PropertyValueFactory<>("emailadresse"));
    	columnEmailadresse.setMinWidth(100.0);
    	columnGeburtstag.setCellValueFactory(new PropertyValueFactory<>("geburtstag"));
    	columnGeburtstag.setMinWidth(100.0);
    	columnWohnort.setCellValueFactory(new PropertyValueFactory<>("wohnort"));
    	columnWohnort.setMinWidth(100.0);
    	columnGeschlecht.setCellValueFactory(new PropertyValueFactory<>("geschlecht"));
    	columnGeschlecht.setMinWidth(100.0);
    	columnEintrittsdatum.setCellValueFactory(new PropertyValueFactory<>("eintrittsdatum"));
    	columnEintrittsdatum.setMinWidth(100.0);
    	columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    	columnRole.setMinWidth(100.0);
    	columnPostleitzahl.setCellValueFactory(new PropertyValueFactory<>("postleitzahl"));
    	columnPostleitzahl.setMinWidth(100.0);
    	columnRFID.setCellValueFactory(new PropertyValueFactory<>("rfidString"));
    	columnRFID.setMinWidth(100.0);
    	columnID.setCellValueFactory(new PropertyValueFactory<>("dbId"));
    	columnID.setMinWidth(100.0);
    	
    }
    private void setData(ObservableList<Teilnehmer> t) {
    	this.teilnehmerTable.setItems(t);
    }
	@Override
	public void setMenu(Menu m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteSignal() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateSignal() {
		// TODO Auto-generated method stub
		setData(TeilnehmerDB.getAll());
	}
}
