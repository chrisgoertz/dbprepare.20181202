package de.ncdf.dbprepare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;

public class DisclaimerController implements GuiPage{
	private String welcome = "This software was writen by Christian Görtz in 2018\n"
			+ "IMPORTANT! This software is still under developement!\n"
			+ "The author of this software is not responsible for any\n"
			+ "data lose or hardware damage!\n"
			+ "THIS SOFTWARE IS LICENSED UNDER GPL";
	
	private String filePath = "unset";
	
	@FXML
	private TextArea tfDisclaimer;
	@FXML
	private Label lblHeading;

	@FXML
	void initialize() {
		assert tfDisclaimer != null : "fx:id=\"tfDisclaimer\" was not injected: check your FXML file 'Disclaimer.fxml'.";
		detectOs();
		String gplString = getGPL();
		System.out.println(gplString);
		tfDisclaimer.appendText(gplString);
		tfDisclaimer.home();
		this.lblHeading.setText(welcome);
	}
	 
	 private void detectOs() {
		 String OS = System.getProperty("os.name").toLowerCase();
		 System.out.println("OS detected: "+OS);
		 if(OS.indexOf("win") >= 0) {
			 filePath = getClass().getResource("gpl-3.0.txt").getPath().substring(1);			 
		 }
		 else {
			 filePath = getClass().getResource("gpl-3.0.txt").getPath();
		 }

	}

	private String getGPL() {
		 StringBuilder ret = new StringBuilder();
		 try (Stream<String> stream = Files.lines(Paths.get(filePath))) 
		 {
			 stream.forEach(s -> ret.append(s).append("\n"));
			 
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		 
		 return ret.toString();
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
