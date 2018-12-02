package de.ncdf.dbprepare;

import java.net.URL;
import java.util.ResourceBundle;

import de.ncdf.dbconnections.LocalPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DBPreferencesController
{
	private LocalPreferences lp = new LocalPreferences();
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSave;

    @FXML
    private TextField tfUsern;

    @FXML
    private TextField tfPort;

    @FXML
    private PasswordField tfPass;

    @FXML
    private TextField tfHost;

    @FXML
    void savePreferences(ActionEvent event) {
    	lp.setHost(tfHost.getText());
    	lp.setPort(Integer.parseInt(tfPort.getText()));
    	lp.setUsern(tfUsern.getText());
    	lp.setUserp(tfPass.getText());
    	lp.save();
    }

    @FXML
    void initialize() {
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'DBPreferences.fxml'.";
        assert tfUsern != null : "fx:id=\"tfUsern\" was not injected: check your FXML file 'DBPreferences.fxml'.";
        assert tfPort != null : "fx:id=\"tfPort\" was not injected: check your FXML file 'DBPreferences.fxml'.";
        assert tfPass != null : "fx:id=\"tfPass\" was not injected: check your FXML file 'DBPreferences.fxml'.";
        assert tfHost != null : "fx:id=\"tfHost\" was not injected: check your FXML file 'DBPreferences.fxml'.";
        this.fetchPreferences();
    }
    
    private void fetchPreferences() {
    	lp.load();
    	tfHost.setText(lp.getHost());
    	tfPort.setText(lp.getPort()+"");
    	tfUsern.setText(lp.getUsern());
    	tfPass.setText(lp.getUserp());
    	
    }
}
