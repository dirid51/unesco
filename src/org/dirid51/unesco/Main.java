package org.dirid51.unesco;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	
	private static final String WORLD_HERITAGE_LIST_URL = "http://whc.unesco.org/en/list/";
	private static final String SAVE_TO_DIR = "C:\\Users\\randallbooth\\Desktop\\unesco";

	public static void main(String[] args) {
		try {
	        new Main().generate();
        } catch (IOException e) {
	        e.printStackTrace();
        }

	}
	
	public void generate() throws IOException {
		Gather gather = new Gather();
		
//		Get country list, along with initial data of each heritage site
		SortedMap<String, HeritageCountry> countries = new TreeMap<>(gather.gatherList(WORLD_HERITAGE_LIST_URL,
													"div#acc h4 a[href^=/en/statesparties/], div.list_site ul li a[href^=/en/list/]",
													SAVE_TO_DIR));
		
//		Generate HTML document - Home Page - and save to local machine
		StringBuilder homeHtml = new StringBuilder("<!DOCTYPE html><html lang=\"en-US\"><meta charset=\"UTF-8\">"
						+ "<head><title>World Heritage Sites</title></head><body><h1>World Heritage Sites</h1><dl><dt>");
		for (String c : countries.keySet()) {
			homeHtml.append("<dt>" + c + "</dt>");
			for (HeritageSite site : countries.get(c).getHeritageSites()) {
				homeHtml.append("<dd><a href=\"" + site.getLocalUrl() + "\">" + site.getName() + "</a></dd>");
			}
		}
		homeHtml.append("</dt></body></html>");		
		Util.writeFile(Paths.get(SAVE_TO_DIR, "unesco_home.html"), homeHtml.toString());
		
//		Generate HTML documents - Site Pages - save to local machine
		Set<HeritageSite> sites = extractSites(countries);
		sites.parallelStream().forEach(s -> {
			Map<String, String> info = gather.gatherText(s.getUrl().toExternalForm(), "div#contentdes_en, div.box:contains(Long Description), div.box:contains(Historical Description), div.box:contains(Outstanding)", "Description");
			StringBuilder siteHtml = new StringBuilder("<!DOCTYPE html><html lang=\"en-US\"><meta charset=\"UTF-8\">"
						+ "<head><title>" + s.getName() + "</title></head><body><h1>" + s.getName() + "</h1>");
			info.values().forEach(t -> siteHtml.append(t));
			siteHtml.append("</body></html>");
			try {
	            Util.writeFile(Paths.get(s.getLocalUrl().toURI()), siteHtml.toString());
            } catch (Exception e) {
	            e.printStackTrace();
            }
		});
	}
	
	private Set<HeritageSite> extractSites(Map<String, HeritageCountry> countries) {
		Set<HeritageSite> sites = new TreeSet<HeritageSite>(new Comparator<HeritageSite>() {

			@Override
            public int compare(HeritageSite o1, HeritageSite o2) {
	            return o1.getName().compareToIgnoreCase(o2.getName());
            }
		});
		
		countries.values().forEach(hc -> sites.addAll(hc.getHeritageSites()));
		
		return sites;
	}

}
