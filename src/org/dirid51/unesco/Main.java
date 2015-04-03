package org.dirid51.unesco;

import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
	
	private static final String WORLD_HERITAGE_LIST_URL = "http://whc.unesco.org/en/list/";
	private SortedMap<String, HeritageCountry> countries;

	public static void main(String[] args) {
		new Main().generate();

	}
	
	public Main() {
	}
	
	public String generate() {
		Gather gather = new Gather();
		countries = new TreeMap<>(gather.gatherList(WORLD_HERITAGE_LIST_URL, "div#acc h4 a[href^=/en/statesparties/], div.list_site ul li a[href^=/en/list/]"));
		
		StringBuilder html = new StringBuilder("<!DOCTYPE html><html><head><title>World Heritage Sites</title></head><body><h1>World Heritage Sites</h1><dl><dt>");
		for (String c : countries.keySet()) {
			html.append("<dt>" + c + "</dt>");
			for (HeritageSite site : countries.get(c).getHeritageSites()) {
				html.append("<dd><a href=\"" + site.getUrl() + "\">" + site.getName() + "</a></dd>");
			}
		}
		html.append("</dt></body></html>");
		
		
		return "Finished!";
	}

}
