package org.dirid51.unesco;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Gather {

//	Selectors: div#acc h4 a[href^=/en/statesparties/], div.list_site ul li a[href^=/en/list/]
	public Map<String, HeritageCountry> gatherList(String url, String cssSelector, String localDir) {
		Map<String, HeritageCountry> heritageCountries = new HashMap<String, HeritageCountry>();

		try {
			System.out.println("Gathering country and site names...");
			Elements cData = Util.pageData(url, cssSelector);
			String mostRecentCountry = null;
			for (Element e : cData) {
				if (e.attr("href").startsWith("/en/statesparties/")) {
					HeritageCountry heritageCountry = new HeritageCountry();
					heritageCountry.setName(e.text());
					heritageCountries.put(e.text(), heritageCountry);
					mostRecentCountry = e.text();
					System.out.println("Country: " + heritageCountry.getName());
				} else {
					if (mostRecentCountry != null) {
						Matcher m = Pattern.compile("^/en/list/(\\d+)$").matcher(e.attr("href"));
						m.matches(); 
						int uNum = Integer.parseInt(m.group(1));
						heritageCountries.get(mostRecentCountry).addHeritageSite(
										new HeritageSite(	e.text(),
															uNum,
															new URL("http://whc.unesco.org" + e.attr("href")),
															new URL("file:///" + localDir + "/site_" + Integer.toString(uNum) + ".html")
										)
						);
						System.out.println("\t\t\tSite: " + e.text());
					}
				}
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		return heritageCountries;
	}

//	div#contentdes_en, div.box:contains(Long Description), div.box:contains(Historical Description), div.box:contains(Outstanding)
	public List<String> gatherText(String url, String cssSelector) {
		List<String> textBlock = new ArrayList<>();
		System.out.println("Gathering text blocks...");
		
		try {
			Elements textData = Util.pageData(url, cssSelector);
			for (Element e : textData) {
				textBlock.add(e.html());
				System.out.println("Text: " + e.html().substring(0, 50));
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return textBlock;
	}
	
//	img.icaption-img
	public List<URL> gatherImages(String url, String cssSelector) {
		List<URL> imagePaths = new ArrayList<>();
		System.out.println("Gathering images...");
		
		try{
			Elements imgs = Util.pageData(url, cssSelector);
			for (Element e : imgs) {
				imagePaths.add(new URL("http://whc.unesco.org" + e.attr("data-src")));
				System.out.println("Image: " + imagePaths.get(imagePaths.size() - 1));
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return imagePaths;
	}
	
}
