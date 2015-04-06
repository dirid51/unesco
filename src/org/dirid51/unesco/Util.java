package org.dirid51.unesco;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class Util {
	// main method included to enable tweak-and-try opportunities
	public static void main(String[] args) {
		
	}

	public static String getHTMLPage(URL url) throws IOException, URISyntaxException {
		// System.out.println("Reading page at: " + url.toString());
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

	public static void writeFile(Path saveTo, String data) throws IOException {
		Files.deleteIfExists(saveTo);
		Files.write(saveTo, data.getBytes());
	}
	
	public static boolean downloadFile(URL fileUrl, Path saveFileHere) {
		boolean retval = true;
        if (!Files.exists(saveFileHere)) {
	        try (ReadableByteChannel rbc = Channels.newChannel(fileUrl.openStream());
	                        FileOutputStream fos = new FileOutputStream(saveFileHere.toFile());) {
		        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	        } catch (IOException e) {
		        retval = false;
	        }
        } else {
	        retval = false;
        }
        
        return retval;
	}

}
