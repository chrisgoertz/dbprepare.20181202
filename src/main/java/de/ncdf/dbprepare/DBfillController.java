package de.ncdf.dbprepare;

import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.StampDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;

public class DBfillController implements GuiPage{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button btnGenerateStampEventToday;

    @FXML
    void initialize() {
    	
    
    }
    @FXML
    void onGenerateStampEventToday(ActionEvent event) {
    	StampDB.insertTest();
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
		
	}
}
