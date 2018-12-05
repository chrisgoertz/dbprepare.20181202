package de.ncdf.dbprepare;
import java.io.IOException;

import de.ncdf.dbconnections.StampDB;
import de.ncdf.dbconnections.VersionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class GuiController {
	
	private VBox pref = null;
	private AnchorPane discl = null;
	private GridPane upDb = null;
	private VBox rfidUebersicht = null;
	
	 @FXML
    private BorderPane mainBorderPane;

    @FXML
    private MenuItem mPreferences;
    @FXML
    private MenuItem mUpdates;
    @FXML
    private MenuItem mStempelUebersicht;
    
    

    @FXML
    void openPreferences(ActionEvent event) {
    	mainBorderPane.setCenter(pref);
    	
    }
    @FXML
    void openUpdates(ActionEvent event) {
    	mainBorderPane.setCenter(upDb);
    }
    @FXML 
    void openDisclaimer(ActionEvent event) {
    	mainBorderPane.setCenter(discl);
    }
    @FXML
    void openStempelUebersicht(ActionEvent event) {
    	mainBorderPane.setCenter(rfidUebersicht);
    }
    
    @FXML
    void initialize() {
        assert mainBorderPane != null : "fx:id=\"mainBorderPane\" was not injected: check your FXML file 'maingui.fxml'.";
        assert mPreferences != null : "fx:id=\"mPreferences\" was not injected: check your FXML file 'maingui.fxml'.";
        loadFXMLs();
        mainBorderPane.setCenter(discl);
        VersionDB db = new VersionDB();
        if(-1 == db.getVersion("versionen")) {
        	db.firstVersion("versionen");
        }
        System.out.printf("Versionen: %d\n",db.getVersion("versionen"));
        StampDB sdb = new StampDB();
    }
    private void loadFXMLs() {
    	try {
    		if (null == pref) {
    			pref = FXMLLoader.load(getClass().getResource("DBPreferences.fxml"));
    		}
    		if (null == discl) {
    			discl = FXMLLoader.load(getClass().getResource("Disclaimer.fxml"));
    		}
    		if (null == upDb) {
    			upDb = FXMLLoader.load(getClass().getResource("DBUpdate.fxml"));
    		}
    		if (null == rfidUebersicht) {
    			rfidUebersicht = FXMLLoader.load(getClass().getResource("RfidTable.fxml"));
    		}
			
		} catch (IOException e) {
			System.err.println("loading fxml-files failed");
			e.printStackTrace();
		}
    }
}
