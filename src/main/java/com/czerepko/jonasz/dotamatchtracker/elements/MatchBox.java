package com.czerepko.jonasz.dotamatchtracker.elements;

import org.joda.time.DateTime;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class providing a box for a single match including information about the date and the teams playing.
 */
public class MatchBox extends HBox {
	
	private final int TEXT_SIZE = 20;
	private final int LOGO_SIZE = 50;
	private final Color TEXT_COLOR = Color.WHITE;
	
	/**
	 * The date of the match including the hour.
	 */
	private DateTime matchDate;
	/**
	 * Name of the first team playing.
	 */
	private String teamOne;
	/**
	 * Name of the second team playing.
	 */
	private String teamTwo;
	/**
	 * Logo of the first team playing.
	 */
	private ImageView teamOneLogo;
	/**
	 * Logo of the second team playing.
	 */
	private ImageView teamTwoLogo;
	
	/**
	 * Constructor creating the whole MatchBox including date, names and logos displayed.
	 * @param matchDate Date when the match starts (including hour).
	 * @param teamOne First team playing the match.
	 * @param teamTwo Second team playing the match.
	 */
	public MatchBox(MatchInfo matchInfo) {
		super(10);
		
		this.matchDate = matchInfo.matchDate;
		this.teamOne = matchInfo.teamOne;
		this.teamTwo = matchInfo.teamTwo;
		
		loadLogos();		
		createMatchBox();
	}
	
	private void loadLogos() {
		teamOneLogo = loadLogo(teamOne);
		teamTwoLogo = loadLogo(teamTwo);
		
		teamOneLogo.setFitWidth(LOGO_SIZE);
		teamOneLogo.setPreserveRatio(true);
		teamTwoLogo.setFitWidth(LOGO_SIZE);
		teamTwoLogo.setPreserveRatio(true);
	}
	
	private ImageView loadLogo(String teamName) {
		switch(teamName) {
			case "Team Secret":
				return new ImageView(getClass().getResource("../resources/secret.png").toString());
			case "Cloud9":
				return new ImageView(getClass().getResource("../resources/cloud9.png").toString());
			case "compLexity Gaming":
				return new ImageView(getClass().getResource("../resources/complexity.png").toString());
			case "EHOME":
				return new ImageView(getClass().getResource("../resources/ehome.png").toString());
			case "Evil Geniuses":
				return new ImageView(getClass().getResource("../resources/eg.png").toString());
			case "Fnatic":
				return new ImageView(getClass().getResource("../resources/fnatic.png").toString());
			case "Invictus Gaming":
				return new ImageView(getClass().getResource("../resources/ig.png").toString());
			case "LGD Gaming":
				return new ImageView(getClass().getResource("../resources/lgd.png").toString());
			case "MVP HOTSIX":
				return new ImageView(getClass().getResource("../resources/mvp6.png").toString());
			case "Na`Vi":
				return new ImageView(getClass().getResource("../resources/navi.png").toString());
			case "Newbee":
				return new ImageView(getClass().getResource("../resources/newbee.png").toString());
			case "Team Empire":
				return new ImageView(getClass().getResource("../resources/empire.png").toString());
			case "Vici Gaming":
				return new ImageView(getClass().getResource("../resources/vg.png").toString());
			case "Virtus.Pro":
				return new ImageView(getClass().getResource("../resources/vp.png").toString());
			default:
				return new ImageView(getClass().getResource("../resources/default.png").toString());
		}
	}

	private void createMatchBox() {
		this.getChildren().add(new Text(""));
		
		Text text = new Text(matchDate.toString("dd-MM-yyyy HH:mm"));
		text.setFill(TEXT_COLOR);
		text.setFont(new Font(TEXT_SIZE));
		this.getChildren().add(text);

		text = new Text(teamOne);
		text.setFill(TEXT_COLOR);
		text.setFont(new Font(TEXT_SIZE));
		this.getChildren().add(text);
		this.getChildren().add(teamOneLogo);
		
		text = new Text("vs");
		text.setFill(TEXT_COLOR);
		text.setFont(new Font(TEXT_SIZE));
		this.getChildren().add(text);
		
		this.getChildren().add(teamTwoLogo);
		text = new Text(teamTwo);
		text.setFill(TEXT_COLOR);
		text.setFont(new Font(TEXT_SIZE));
		this.getChildren().add(text);
	}
}
