package org.dirid51.unesco;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

	private static final String WORLD_HERITAGE_LIST_URL = "http://whc.unesco.org/en/list/";
	private static final String SAVE_TO_DIR = "C:\\Users\\randallbooth\\Desktop\\unesco";
	private static final String[] REQUIRED_FILE_PATHS = {"/resources/styles/substyle.css","/resources/styles/homestyle.css"};

	public static void main(String[] args) {
		try {
			Instant begin = Instant.ofEpochMilli(System.currentTimeMillis());
			new Main().generate();
			Duration elapsed = Duration.between(begin, Instant.ofEpochMilli(System.currentTimeMillis()));
			System.out.println("Time to complete: " + elapsed.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void generate() throws IOException {
		Gather gather = new Gather();

		// Get country list, along with initial data of each heritage site
		SortedMap<String, HeritageCountry> countries = new TreeMap<>(gather.gatherList(WORLD_HERITAGE_LIST_URL,
		                "div#acc h4 a[href^=/en/statesparties/], div.list_site ul li a[href^=/en/list/]", SAVE_TO_DIR));

		// Generate HTML document - Home Page - and save to local machine
		StringBuilder homeHtml = new StringBuilder("<!DOCTYPE html><html lang=\"en-US\"><meta charset=\"UTF-8\">"
						+ "<link rel=\"stylesheet\" href=\"dr_reset.css\"><link rel=\"stylesheet\" href=\"homestyle.css\">"
		                + "<title>World Heritage Sites</title><body><section id=\"main\"><header><h1>World Heritage Sites</h1></header><nav>Home</nav><section id=\"siteList\"><dl><dt>");
		for (String c : countries.keySet()) {
			homeHtml.append("<dt>" + c + "</dt>");
			for (HeritageSite site : countries.get(c).getHeritageSites()) {
				homeHtml.append("<dd><a href=\"" + site.getLocalUrl() + "\">" + site.getName() + "</a></dd>");
			}
		}
		homeHtml.append("</dt></section><footer>Source Page: <a href=\"" + WORLD_HERITAGE_LIST_URL + "\">" + WORLD_HERITAGE_LIST_URL + "</a></footer></body></html>");
		Util.writeFile(Paths.get(SAVE_TO_DIR, "unesco_home.html"), homeHtml.toString());

		// Generate HTML documents - Site Pages - save to local machine
		Set<HeritageSite> sites = extractSites(countries);
		sites.parallelStream()
		                .forEach(s -> {
		                	
//		                	Scrape data/images from whc.unesco.org
			                List<URL> imageUrls = gather.gatherImages("http://whc.unesco.org/pg.cfm?cid=31&l=en&id_site=" + s.getUnescoNumber()
			                                + "&gallery=1&&maxrows=100", "img.icaption-img");
			                s.addImagePaths(downloadImages(imageUrls, s.getUnescoNumber()));
			                s.addTextDescriptions(gather
			                                .gatherText(s.getUrl().toExternalForm(),
			                                                "div#contentdes_en, div.box:contains(Long Description), div.box:contains(Historical Description), div.box:contains(Outstanding)"));
			                
//			                Create UNESCO site web page
			                StringBuilder siteHtml = new StringBuilder("<!DOCTYPE html><html lang=\"en-US\"><meta charset=\"UTF-8\"><title>"
			                                + s.getName() + "</title><link rel=\"stylesheet\" href=\"dr_reset.css\"><link rel=\"stylesheet\" href=\"substyle.css\"><body><section id=\"main\"><header><h1>" + s.getName() + "</h1></header>");
			                siteHtml.append("<nav><a href=\"unesco_home.html\">Home</a>&nbsp;&gt;&nbsp;" + s.getName() + "</nav>");
			                siteHtml.append("<section id=\"images\"><p>");
			                s.getImagePaths().forEach(ip -> {
			                	try {
	                                siteHtml.append("<img src=\"" + (ip.toUri().toURL().toExternalForm()) + "\">");
                                } catch (Exception e) {
	                                e.printStackTrace();
                                }
			                });
			                siteHtml.append("</p></section><section id=\"text\">");
			                s.getTextDescriptions().forEach(t -> siteHtml.append("<article>" + t + "</article>"));
			                siteHtml.append("</section></section><footer>Source Page: <a href=\"" + s.getUrl().toExternalForm() + "\">" + s.getUrl().toExternalForm() + "</a></footer></body></html>");
			                try {
				                Util.writeFile(Paths.get(s.getLocalUrl().toURI()), siteHtml.toString());
			                } catch (Exception e) {
				                e.printStackTrace();
			                }
		                });
		
//		Copy over styles, scripts, etc.		
		Arrays.asList(REQUIRED_FILE_PATHS).stream()
		.forEach(s -> {
			try {
				Path source = Paths.get(s);
				Path dest = Paths.get(SAVE_TO_DIR, source.getFileName().toString());
	            Files.copy(source, dest);
            } catch (Exception e) {
	            e.printStackTrace();
            }
			
		});
	}

	private Set<Path> downloadImages(List<URL> imageUrls, int unescoNumber) {
		Set<Path> imagePaths = new HashSet<Path>(imageUrls.size());
		try {
			Path dirPath = Paths.get(SAVE_TO_DIR, "images");
	        Files.createDirectories(dirPath);
			for (URL url : imageUrls) {
				Path imgPath = Paths.get(dirPath.toString(), "img-" + unescoNumber + "-" + imageUrls.indexOf(url) + ".jpg");
				if (Files.exists(imgPath) || Util.downloadFile(url, imgPath)) {
					imagePaths.add(imgPath);
					System.out.println("Downloaded file " + imgPath);
				}
			}
        } catch (IOException e) {
	        e.printStackTrace();
        }
		return imagePaths;
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
