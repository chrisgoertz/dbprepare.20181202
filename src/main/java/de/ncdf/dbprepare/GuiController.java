package de.ncdf.dbprepare;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class GuiController {
	
	private VBox pref = null;
	private AnchorPane discl = null;
	private GridPane upDb = null;
	 @FXML
    private BorderPane mainBorderPane;

    @FXML
    private MenuItem mPreferences;
    @FXML
    private MenuItem mUpdates;
    

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
    void initialize() {
        assert mainBorderPane != null : "fx:id=\"mainBorderPane\" was not injected: check your FXML file 'maingui.fxml'.";
        assert mPreferences != null : "fx:id=\"mPreferences\" was not injected: check your FXML file 'maingui.fxml'.";
        loadFXMLs();
        mainBorderPane.setCenter(discl);
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
			
		} catch (IOException e) {
			System.err.println("loading fxml-files failed");
			e.printStackTrace();
		}
    }
}
