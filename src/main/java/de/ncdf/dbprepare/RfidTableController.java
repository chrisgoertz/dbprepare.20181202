package de.ncdf.dbprepare;


import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.StampDB;
import de.ncdf.models.RFIDTag;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RfidTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<RFIDTag> rfidTable;

    @FXML
    private TableColumn<RFIDTag, String> columnRDID;

    @FXML
    private TableColumn<RFIDTag, String> columnStatus;

    @FXML
    private TableColumn<RFIDTag, String> columnZeitpunkt;

    @FXML
    void initialize() {
        assert rfidTable != null : "fx:id=\"rfidTable\" was not injected: check your FXML file 'RfidTable.fxml'.";
        assert columnRDID != null : "fx:id=\"columnRDID\" was not injected: check your FXML file 'RfidTable.fxml'.";
        assert columnStatus != null : "fx:id=\"columnStatus\" was not injected: check your FXML file 'RfidTable.fxml'.";
        assert columnZeitpunkt != null : "fx:id=\"columnZeitpunkt\" was not injected: check your FXML file 'RfidTable.fxml'.";

    }
    private void getStamps() {
    	StampDB sd = new StampDB();
    	
    }
}
