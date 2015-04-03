package org.dirid51.unesco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class Util {
	// main method included to enable tweak-and-try opportunities 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String getHTMLPage(URL url) throws IOException, URISyntaxException {
//		System.out.println("Reading page at: " + url.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        StringBuilder result = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
	        result.append(inputLine);
        }
        in.close();
        return result.toString();
	}
	
	public static String getHTMLPage(String url) throws IOException, URISyntaxException {
		return getHTMLPage(new URL(url));
	}

	public static Elements pageData(String url, String cssSelector) throws MalformedURLException, IOException, URISyntaxException {
		return Jsoup.parse(getHTMLPage(new URL(url))).select(cssSelector);
	}

}
