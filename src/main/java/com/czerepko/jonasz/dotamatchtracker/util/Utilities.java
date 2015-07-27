package com.czerepko.jonasz.dotamatchtracker.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Class containing utility methods used in the Dota Match Tracker project.
 */
public class Utilities {
	
	private static final String STREAM1_URL = "http://www.twitch.tv/dota2ti";
	private static final String STREAM2_URL = "http://www.twitch.tv/dota2ti_2";
	private static final String STREAM3_URL = "http://www.twitch.tv/dota2ti_3";
	private static final String STREAM_NOOB_URL = "http://www.twitch.tv/dota2ti_newcomer";
	
	/**
	 * Static method that returns a HBox component having 4 buttons redirecting to The International streams.
	 * @return HBox component having 4 buttons redirecting to The International streams.
	 */
	public static HBox addStreamButtons() {
		HBox hbox = new HBox(5);
		hbox.setAlignment(Pos.CENTER);
		
		for (int i = 0; i < 4; i++)
			hbox.getChildren().add(addStreamButton(i));
		
		return hbox;
	}
	
	private static Button addStreamButton(int number) {
		Button button = new Button();
		
		if (number == 0)
			button.setText("Newcomer stream");
		else
			button.setText("Stream #" + number);
		
		button.setOnAction(openStream(number));
		
		return button;
	}
	
	private static EventHandler<ActionEvent> openStream(int number) {
		EventHandler<ActionEvent> event = null;
		switch(number) {
			case 1:
				return new EventHandler<ActionEvent>() {
				    public void handle(ActionEvent event) {
				    	try {
							Desktop.getDesktop().browse(new URI(STREAM1_URL));
						} catch (IOException | URISyntaxException e) {
							Platform.exit();
						}
				    	event.consume();
				    }
			    };
			case 2:
				return new EventHandler<ActionEvent>() {
				    public void handle(ActionEvent event) {
				    	try {
							Desktop.getDesktop().browse(new URI(STREAM2_URL));
						} catch (IOException | URISyntaxException e) {
							Platform.exit();
						}
				    	event.consume();
				    }
			    };
			case 3:
				return new EventHandler<ActionEvent>() {
				    public void handle(ActionEvent event) {
				    	try {
							Desktop.getDesktop().browse(new URI(STREAM3_URL));
						} catch (IOException | URISyntaxException e) {
							Platform.exit();
						}
				    	event.consume();
				    }
			    };
			case 0:
				return new EventHandler<ActionEvent>() {
				    public void handle(ActionEvent event) {
				    	try {
							Desktop.getDesktop().browse(new URI(STREAM_NOOB_URL));
						} catch (IOException | URISyntaxException e) {
							Platform.exit();
						}
				    	event.consume();
				    }
			    };
		}
		
	    
	    return event;
	}
	
}
