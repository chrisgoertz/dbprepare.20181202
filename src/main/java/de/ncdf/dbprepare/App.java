package de.ncdf.dbprepare;

import de.ncdf.dbconnections.LocalPreferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
    	
    	LocalPreferences.mkTable();
        launch(args);
    }

	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("DB-Prepare");
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("maingui.fxml"));
		//AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("gui.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
