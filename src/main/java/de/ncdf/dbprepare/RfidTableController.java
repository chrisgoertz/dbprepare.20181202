package de.ncdf.dbprepare;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Observable;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.StampDB;
import de.ncdf.models.RFIDTag;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RfidTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<RFIDTag> rfidTable;

    @FXML
    private TableColumn<RFIDTag, String> columnRFID;

    @FXML
    private TableColumn<RFIDTag, String> columnStatus;

    @FXML
    private TableColumn<RFIDTag, String> columnZeitpunkt;
    @FXML
    private DatePicker dpVon;
    @FXML
    private DatePicker dpBis;
    
    @FXML
    void initialize() {
        assert rfidTable != null : "fx:id=\"rfidTable\" was not injected: check your FXML file 'RfidTable.fxml'.";
        assert columnRFID != null : "fx:id=\"columnRDID\" was not injected: check your FXML file 'RfidTable.fxml'.";
        assert columnStatus != null : "fx:id=\"columnStatus\" was not injected: check your FXML file 'RfidTable.fxml'.";
        assert columnZeitpunkt != null : "fx:id=\"columnZeitpunkt\" was not injected: check your FXML file 'RfidTable.fxml'.";
        initTable();
    }
    
    private void initTable() {
    	columnRFID.setCellValueFactory(new PropertyValueFactory<>("tagString"));
    	columnRFID.setMinWidth(100.0);
    	columnStatus.setCellValueFactory(new PropertyValueFactory<>("statusString"));
    	columnStatus.setMinWidth(40.0);
    	columnZeitpunkt.setCellValueFactory(new PropertyValueFactory<>("timeStampString"));
    	columnZeitpunkt.setMinWidth(150.0);
    	getStamps();
    }
    private void getStamps() {
    	StampDB sd = new StampDB();
    	ObservableList<RFIDTag> tagList = sd.getAll();
    	rfidTable.setItems(tagList);
    	
    }
    @FXML
    private void rangeModified(ActionEvent e) {
    	StampDB sd = new StampDB();
    	LocalDate fromDate = dpVon.getValue() != null ? dpVon.getValue() : LocalDate.now();
    	LocalDate toDate = dpBis.getValue() != null ? dpBis.getValue() : LocalDate.now().plusDays(1);
    	ObservableList<RFIDTag> tagList = sd.getRange(fromDate, toDate);
    	rfidTable.setItems(tagList);
    }
}
