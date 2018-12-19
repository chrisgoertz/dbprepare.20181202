package de.ncdf.dbprepare;


import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.VersionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class DBUpdateController implements GuiPage{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblDb6;

    @FXML
    private Label lblDb4;

    @FXML
    private Label lblDb5;

    @FXML
    private Button btnDb1;

    @FXML
    private Button btnDb2;

    @FXML
    private Button btnDb3;

    @FXML
    private Button btnDb4;

    @FXML
    private Button btnDb5;

    @FXML
    private Label lblDb2;

    @FXML
    private Button btnDb6;

    @FXML
    private Label lblDb3;

    @FXML
    private Label lblDb1;

    @FXML
    void btnDb1Action(ActionEvent event) {

    }

    @FXML
    void btnDb2Action(ActionEvent event) {

    }

    @FXML
    void btnDb3Action(ActionEvent event) {

    }

    @FXML
    void btnDb4Action(ActionEvent event) {

    }

    @FXML
    void btnDb5Action(ActionEvent event) {

    }

    @FXML
    void btnDb6Action(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert lblDb6 != null : "fx:id=\"lblDb6\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert lblDb4 != null : "fx:id=\"lblDb4\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert lblDb5 != null : "fx:id=\"lblDb5\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert btnDb1 != null : "fx:id=\"btnDb1\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert btnDb2 != null : "fx:id=\"btnDb2\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert btnDb3 != null : "fx:id=\"btnDb3\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert btnDb4 != null : "fx:id=\"btnDb4\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert btnDb5 != null : "fx:id=\"btnDb5\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert lblDb2 != null : "fx:id=\"lblDb2\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert btnDb6 != null : "fx:id=\"btnDb6\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert lblDb3 != null : "fx:id=\"lblDb3\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        assert lblDb1 != null : "fx:id=\"lblDb1\" was not injected: check your FXML file 'DBUpdate.fxml'.";
        showVersions();
        initBtns();
    }
    
    private void showVersions() {
    	VersionDB vdb = new VersionDB();
    	int versiondb = vdb.getVersion("versionen");
    	lblDb1.setText("Tabelle Versionen: v"+versiondb);
    	int stampeventsdb = vdb.getVersion("stampevents");
    	lblDb2.setText("Tabelle Stampevents: v"+stampeventsdb);
    	int personendb = vdb.getVersion("personen");
    	lblDb3.setText("Tabelle Personen: v"+personendb);
    	
    }
    
    private void initBtns() {
    	this.btnDb1.setText("update");
    	this.btnDb1.setDisable(true);
    	this.btnDb2.setText("update");
    	this.btnDb2.setDisable(true);
    	this.btnDb3.setText("update");
    	this.btnDb3.setDisable(true);
    	this.btnDb4.setText("update");
    	this.btnDb4.setDisable(true);
    	this.btnDb5.setText("update");
    	this.btnDb5.setDisable(true);
    	this.btnDb6.setText("update");
    	this.btnDb6.setDisable(true);
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
