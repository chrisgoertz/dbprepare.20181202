package de.ncdf.dbprepare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DisclaimerController {
	private String welcome = "This software was writen by Christian Görtz in 2018\n"
			+ "IMPORTANT! This software is still under developement!\n"
			+ "The author of this software is not responsible for any\n"
			+ "data lose or hardware damage!\n"
			+ "THIS SOFTWARE IS LICENSED UNDER GPL";
			
	private String filePath = getClass().getResource("gpl-3.0.txt").getPath().substring(1);
	@FXML
	private TextArea tfDisclaimer;
	@FXML
	private Label lblHeading;

	@FXML
	void initialize() {
		assert tfDisclaimer != null : "fx:id=\"tfDisclaimer\" was not injected: check your FXML file 'Disclaimer.fxml'.";
		System.out.println(filePath);
		tfDisclaimer.appendText(getGPL());
		tfDisclaimer.home();
		this.lblHeading.setText(welcome);
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
}
