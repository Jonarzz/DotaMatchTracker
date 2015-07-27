package com.czerepko.jonasz.dotamatchtracker.view;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;

import com.czerepko.jonasz.dotamatchtracker.elements.MatchBox;
import com.czerepko.jonasz.dotamatchtracker.elements.MatchInfo;
import com.czerepko.jonasz.dotamatchtracker.util.Utilities;
import com.czerepko.jonasz.dotamatchtracker.web.HTMLParser;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main window of the Dota Match Tracker.
 */
public class DotaMatchTracker extends Application {
	
	private final String WINDOW_TITLE = "Dota Match Tracker";
	
	private Stage primaryStage;	
	private VBox mainLayout;
	
	private ArrayList<MatchInfo> matchInfo;
	
    public static void main(String[] args) {
    	launch(args);
    }

	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		this.primaryStage.setTitle(WINDOW_TITLE);
		this.primaryStage.setResizable(false);
		
		initRootLayout();
		addAplicationToTray();
	}
	
	private void initRootLayout() {  
		HBox hbox = new HBox();
		
        mainLayout = new VBox(5);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.rgb(31, 31, 31), null, null)));
        
        primaryStage.setMinHeight(0);
        primaryStage.setMaxHeight(500);
        mainLayout.minHeight(0);
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(mainLayout);
        hbox.getChildren().add(sp);
        sp.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        mainLayout.getChildren().add(Utilities.addStreamButtons());
        
        HTMLParser hp = null;
        try {
			hp = new HTMLParser("http://www.dotacinema.com/tournament/the-international-2015");
		} catch (IOException e) {
			e.printStackTrace();
		}
        hp.setTableElements(" ");
        matchInfo = hp.getMatchesFromTable();
        for (MatchInfo m : matchInfo)
        	 mainLayout.getChildren().add(new MatchBox(m));
        
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
            	primaryStage.hide();
                event.consume();
            }
        });
        
        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void addAplicationToTray() {
		SwingUtilities.invokeLater(this::addAppToTray);
	}
	
	private void addAppToTray() {
		Toolkit.getDefaultToolkit();
		
		if (!SystemTray.isSupported()) {
            Platform.exit();
        }
		
		SystemTray tray = SystemTray.getSystemTray();
		Image image = null;

		try {
			image = ImageIO.read(getClass().getResource("resources/tray.png"));
		} catch (IOException e) {
			Platform.exit();
		}
		TrayIcon trayIcon = new TrayIcon(image);
		trayIcon.setImageAutoSize(true);
		
		trayIcon.addActionListener(event -> Platform.runLater(this::showStage));
		
		MenuItem openItem = new MenuItem("Dota Match Tracker");
        openItem.addActionListener(event -> Platform.runLater(this::showStage));
		
		MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(event -> {
        	tray.remove(trayIcon);
            Platform.exit();
        });
        
        Font boldFont = Font.decode(null).deriveFont(Font.BOLD);
        openItem.setFont(boldFont);

        PopupMenu popup = new PopupMenu();
        popup.add(openItem);
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);
        
        String tooltip = Utilities.getNextMatch(matchInfo);
        
        if (tooltip == null)
        	trayIcon.setToolTip("Cannot find next match");
        else
        	trayIcon.setToolTip("Next match: " + tooltip);
        
        try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			Platform.exit();
		}
	}
	
	private void showStage() {
        if (primaryStage != null) {
        	primaryStage.show();
        	primaryStage.toFront();
        }
    }
}
