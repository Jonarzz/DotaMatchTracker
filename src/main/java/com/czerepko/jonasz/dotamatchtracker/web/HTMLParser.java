package com.czerepko.jonasz.dotamatchtracker.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.czerepko.jonasz.dotamatchtracker.elements.MatchInfo;

public class HTMLParser {
	
	private Document doc;
	private Elements tableElements;
	
	/**
	 * Creates a Document object on which all actions will be performed.
	 * @param URL URL of the page to parse.
	 * @throws IOException
	 */
	public HTMLParser(String URL) throws IOException {
		doc = Jsoup.connect(URL).timeout(0).get();
	}
	
	public ArrayList<MatchInfo> getMatchesFromTable() {
		ArrayList<MatchInfo> matchInfo = new ArrayList<MatchInfo>();
		
		String date, team1, team2;
		ArrayList<String> splitDate;
		int temp;
		
		DateTime matchDate;
		
		for (Element e : tableElements) {
			date = e.select("td[class=match_date]").html();
			date = date.substring(temp = date.indexOf('>') + 1, date.indexOf('<', temp));
			splitDate = new ArrayList<String>(Arrays.asList(date.split(" ")));
			matchDate = setMatchDate(splitDate);
			matchDate = matchDate.withZone(DateTimeZone.getDefault());
			
			team1 = e.select("td[class=match_team_one ]").html();
			team1 = team1.substring(0, team1.indexOf('<') - 1);
			team1 = team1.trim();
			
			team2 = e.select("td[class=match_team ]").html();
			team2 = team2.substring(team2.indexOf('>') + 1, team2.length());
			team2 = team2.trim();
			
			matchInfo.add(new MatchInfo(matchDate, team1, team2));
		}
		
		return matchInfo;
	}
	
	private DateTime setMatchDate(ArrayList<String> list) {
		String temp;
		MutableDateTime date = null;
		int month, day, hour, minute;

		if (list.size() == 2) {
			if ("Today".equals(list.get(0)))
				date = new MutableDateTime(DateTimeZone.UTC);
			else if ("Tomorrow".equals(list.get(0))) 
				date = new MutableDateTime(new DateTime(DateTimeZone.UTC).plusDays(1));
			
			temp = list.get(1);
			
			hour = Integer.parseInt(temp.substring(1, 3));
			if ("pm".equals(temp.substring(temp.length() - 2, temp.length())))
				hour += 12;
			date.setHourOfDay(hour);
			
			date.setMinuteOfHour(Integer.parseInt(temp.substring(4, 6)));
		}
		else if (list.size() == 3) {
			if ("Jul".equals(list.get(1)))
				month = 7;
			else if ("Aug".equals(list.get(1)))
				month = 8;
			else
				month = 1;

			temp = list.get(0);
			day = Integer.parseInt(temp.substring(0, temp.length() - 2));
			
			temp = list.get(2);
			hour = Integer.parseInt(temp.substring(1, 3));
			if ("pm".equals(temp.substring(temp.length() - 2, temp.length())))
				hour += 12;
			
			minute = Integer.parseInt(temp.substring(4, 6));
			
			date = new MutableDateTime(new DateTime(2015, month, day, hour, minute, DateTimeZone.UTC));
		}
		
		return date.toDateTime();
	}
	
	/**
	 * Sets private field tableElements based on given tableClass. Takes elements from matches table. 
	 * <br>
	 * <br>
	 * &#60;tr class="tableClass"&#62;...&#60;tr&#62;
	 * @param tableClass Name of the table class.
	 */
	public void setTableElements(String tableClass) {
		Element matchesTable = doc.select("table[class=matches]").first();
		tableElements = matchesTable.select("tr[class=" + tableClass + ']');
	}
	
	/**
	 * Method returning elements of the table. Used for testing purposes.
	 * @return Elements of the table set by {@link #setTableElements(String) setTableElements} method.
	 */
	public Elements getTableElements() {
		return tableElements;
	}
}
