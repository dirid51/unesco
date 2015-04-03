package org.dirid51.unesco;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Gather {

//	Selectors: div#acc h4 a[href^=/en/statesparties/], div.list_site ul li a[href^=/en/list/]
	public Map<String, Country> gatherList(String url, String cssSelector) {
		Map<String, Country> countries = new HashMap<String, Country>();

		try {
			Elements cData = Util.pageData(url, cssSelector);
			String mostRecentCountry = null;
			for (Element e : cData) {
				if (e.attr("href").startsWith("/en/statesparties/")) {
					Country country = new Country();
					country.setName(e.text());
					countries.put(e.text(), country);
					mostRecentCountry = e.text();
				} else {
					if (mostRecentCountry != null) {
						countries.get(mostRecentCountry).addHeritageSite(new HeritageSite(e.text(), new URL(e.attr("href"))));
					}
				}
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		return countries;
	}

//	div#contentdes_en, div.box:contains(Long Description), div.box:contains(Historical Description), div.box:contains(Outstanding)
	public Map<String, String> gatherText(String url, String cssSelector, String blockTitle) {
		Map<String, String> textBlock = new HashMap<>();
		
		try {
			Elements textData = Util.pageData(url, cssSelector);
			for (Element e : textData) {
				textBlock.put(blockTitle, e.html());
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return textBlock;
	}
	
}
