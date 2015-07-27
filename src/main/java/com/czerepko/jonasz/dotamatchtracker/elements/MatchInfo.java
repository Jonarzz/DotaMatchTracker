package com.czerepko.jonasz.dotamatchtracker.elements;

import org.joda.time.DateTime;

/**
 * Info about Dota match including date and team names.
 */
public class MatchInfo {
	
	/**
	 * The date of the match including the hour.
	 */
	public DateTime matchDate;
	/**
	 * Name of the first team playing.
	 */
	public String teamOne;
	/**
	 * Name of the second team playing.
	 */
	public String teamTwo;
	
	public MatchInfo(int month, int day, int hour, int minute, String teamOne, String teamTwo) {
		matchDate = new DateTime(2015, month, day, hour, minute);
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
	}
	
	public MatchInfo(DateTime matchDate, String teamOne, String teamTwo) {
		this.matchDate = matchDate;
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
	}
	
	public String toString() {
		return matchDate.toString("dd-MM-yyyy HH:mm") + " " + teamOne + " vs " + teamTwo;
	}
}
