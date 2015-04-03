package org.dirid51.unesco;

import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class HeritageSite {
	private String name;
	private URL url;
	private URL localUrl;
	private Map<String, String> textDescriptions;
	private Set<Path> imagePaths;

	public HeritageSite() {
	}

	public HeritageSite(String name) {
		this.name = name;
	}

	public HeritageSite(String name, URL url, URL localUrl) {
		this.name = name;
		this.url = url;
		this.localUrl = localUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(URL localUrl) {
		this.localUrl = localUrl;
	}

	public Map<String, String> getTextDescriptions() {
		return textDescriptions;
	}

	// public void setTextDescriptions(Map<String, String> textDescriptions) {
	// this.textDescriptions = textDescriptions;
	// }

	public String addTextDescription(String blockTitle, String text) {
		return this.textDescriptions.put(blockTitle, text);
	}

	public Set<Path> getImagePaths() {
		return imagePaths;
	}

	// public void setImagePaths(Set<Path> imagePaths) {
	// this.imagePaths = imagePaths;
	// }

	public boolean addImagePath(Path imagePath) {
		return imagePaths.add(imagePath);
	}

}
