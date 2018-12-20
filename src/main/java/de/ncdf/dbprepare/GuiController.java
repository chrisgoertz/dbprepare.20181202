package de.ncdf.dbprepare;
import java.io.IOException;

import de.ncdf.dbconnections.PersonenDB;
import de.ncdf.dbconnections.StampDB;
import de.ncdf.dbconnections.TeilnehmerDB;
import de.ncdf.dbconnections.VersionDB;
import de.ncdf.models.Teilnehmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class GuiController {
	
	private VBox pref = null;
	private AnchorPane discl = null;
	private GridPane upDb = null;
	private VBox rfidUebersicht = null;
	private VBox tnAnlegen = null;
	private VBox teilnehmerUebersicht = null;
	//FXML-Controller
	private PersonTableController personTableController = null;
	private DBPreferencesController preferencesController = null;
	private DBUpdateController updateController = null;
	private DisclaimerController disclaimerController = null;
	private RfidTableController rfidTableController = null;
	private TeilnehmerAnlegenController teilnehmerAnlegenController = null;
	private GuiPage activePageController = null;
	
	 @FXML
    private BorderPane mainBorderPane;

    @FXML
    private MenuItem mPreferences;
    @FXML
    private MenuItem mUpdates;
    @FXML
    private MenuItem mStempelUebersicht;
    @FXML
    private MenuItem mTeilnehmerAnlegen;
    @FXML
    private MenuItem mTeilnehmerAnzeigen;
    @FXML
    private Menu mAnzeige;
    @FXML
    private MenuItem mDataUpdate;

    @FXML
    void openPreferences(ActionEvent event) {
    	mDataUpdate.setDisable(true);
    	mainBorderPane.setCenter(pref);
    	activePageController = this.preferencesController;
    	
    }
    @FXML
    void openUpdates(ActionEvent event) {
    	mDataUpdate.setDisable(true);
    	mainBorderPane.setCenter(upDb);
    	activePageController = this.updateController;
    }
    @FXML 
    void openDisclaimer(ActionEvent event) {
    	mDataUpdate.setDisable(true);
    	mainBorderPane.setCenter(discl);
    	activePageController = this.disclaimerController;
    }
    @FXML
    void openStempelUebersicht(ActionEvent event) {
    	mDataUpdate.setDisable(false);
    	activePageController = this.rfidTableController;
    	activePageController.updateSignal();
    	mainBorderPane.setCenter(rfidUebersicht);
    }
    @FXML
    void openTeilnehmerAnlegen(ActionEvent event) {
    	//TODO:resize window
    	mDataUpdate.setDisable(true);
    	activePageController = this.teilnehmerAnlegenController;
    	mainBorderPane.setCenter(tnAnlegen);
    }
    @FXML
    void openTeilnehmerAnzeigen(ActionEvent event) {
    	mDataUpdate.setDisable(false);
    	activePageController = this.personTableController;
    	activePageController.updateSignal();
    	mainBorderPane.setCenter(teilnehmerUebersicht);
    }
    @FXML 
    void dataUpdate(ActionEvent event) {
    	activePageController.updateSignal();
    }
    
    @FXML
    void initialize() {
        assert mainBorderPane != null : "fx:id=\"mainBorderPane\" was not injected: check your FXML file 'maingui.fxml'.";
        assert mPreferences != null : "fx:id=\"mPreferences\" was not injected: check your FXML file 'maingui.fxml'.";
        loadFXMLs();
        mDataUpdate.setDisable(true);
        mainBorderPane.setCenter(discl);
        VersionDB db = new VersionDB();
        if(-1 == db.getVersion("versionen")) {
        	db.firstVersion("versionen");
        	StampDB.createTable();
        	db.firstVersion("stampevents");
        	PersonenDB.createTable();
        	db.firstVersion("personen");
        	
        	
        }
        System.out.printf("Versionen: %d\n",db.getVersion("versionen"));
        StampDB sdb = new StampDB("stampevents","zeiterfassung");
        PersonenDB pdb = new PersonenDB();
    }
    
    private void loadFXMLs() {
    	FXMLLoader loader = new FXMLLoader();
    	try {
    		if (null == pref) {
    			loader = new FXMLLoader(getClass().getResource("DBPreferences.fxml"));
    			pref = loader.load();
    			preferencesController = loader.getController();
    		}
    		if (null == discl) {
    			loader = new FXMLLoader(getClass().getResource("Disclaimer.fxml"));
    			discl = loader.load();
    			disclaimerController = loader.getController();
    		}
    		if (null == upDb) {
    			loader = new FXMLLoader(getClass().getResource("DBUpdate.fxml"));
    			upDb = loader.load();
    			updateController = loader.getController();
    		}
    		if (null == rfidUebersicht) {
    			loader = new FXMLLoader(getClass().getResource("RfidTable.fxml"));
    			rfidUebersicht = loader.load();
    			rfidTableController = loader.getController();
    		}
    		if (null == tnAnlegen) {
    			loader = new FXMLLoader(getClass().getResource("TeilnehmerAnlegen.fxml"));
    			tnAnlegen = loader.load();
    			teilnehmerAnlegenController = loader.getController();
    		}
    		if (null == teilnehmerUebersicht) {
    			//FXMLLoader loader = new FXMLLoader(getClass().getResource("SimplePersonTable.fxml"));
    			loader = new FXMLLoader(getClass().getResource("SimplePersonTable.fxml"));
    			//teilnehmerUebersicht = loader.load(getClass().getResource("SimplePersonTable.fxml"));
    			teilnehmerUebersicht = loader.load();
    			personTableController = loader.getController();
    		}
			
		} catch (IOException e) {
			System.err.println("loading fxml-files failed");
			e.printStackTrace();
		}
    }
    
}
