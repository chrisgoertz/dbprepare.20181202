package de.ncdf.dbprepare;

import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.models.Anwesenheit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleButton;

public class AnwesenheitController implements GuiPage{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Anwesenheit, String> columnVorname;

    @FXML
    private ToggleButton btnAbwesend;

    @FXML
    private ToggleButton btnAnwesend;

    @FXML
    private ToggleButton btnVerspaetet;

    @FXML
    private TableColumn<Anwesenheit, String> columnNachname;

    @FXML
    private TableColumn<Anwesenheit, String> columnKommt;

    @FXML
    private TableColumn<Anwesenheit, String> columnGeht;

    @FXML
    private TableColumn<Anwesenheit, ToggleButton> columnBuchen;

    @FXML
    private TableColumn<Anwesenheit, String> columnKonto;

    @FXML
    void onActionAnwesend(ActionEvent event) {

    }

    @FXML
    void onActionAbwesend(ActionEvent event) {

    }

    @FXML
    void onActionVerspaetet(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert columnVorname != null : "fx:id=\"columnVorname\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert btnAbwesend != null : "fx:id=\"btnAbwesend\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert btnAnwesend != null : "fx:id=\"btnAnwesend\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert btnVerspaetet != null : "fx:id=\"btnVerspaetet\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert columnNachname != null : "fx:id=\"columnNachname\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert columnKommt != null : "fx:id=\"columnKommt\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert columnGeht != null : "fx:id=\"columnGeht\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert columnBuchen != null : "fx:id=\"columnBuchen\" was not injected: check your FXML file 'Anwesenheit.fxml'.";
        assert columnKonto != null : "fx:id=\"columnKonto\" was not injected: check your FXML file 'Anwesenheit.fxml'.";

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
