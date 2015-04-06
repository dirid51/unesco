package org.dirid51.unesco;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HeritageSite {
	private String name;
	private int unescoNumber;
	private URL url;
	private URL localUrl;
	private List<String> textDescriptions;
	private Set<Path> imagePaths;

	public HeritageSite() {
		imagePaths = Collections.synchronizedSet(new HashSet<Path>());
		textDescriptions = Collections.synchronizedList(new ArrayList<>());
	}

	public HeritageSite(String name) {
		this();
		this.name = name;
	}

	public HeritageSite(String name, int unescoNumber, URL url, URL localUrl) {
		this();
		this.name = name;
		this.unescoNumber = unescoNumber;
		this.url = url;
		this.localUrl = localUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnescoNumber() {
		return unescoNumber;
	}

	public void setUnescoNumber(int unescoNumber) {
		this.unescoNumber = unescoNumber;
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

	public List<String> getTextDescriptions() {
		return textDescriptions;
	}

	// public void setTextDescriptions(Map<String, String> textDescriptions) {
	// this.textDescriptions = textDescriptions;
	// }

	public boolean addTextDescription(String text) {
		return this.textDescriptions.add(text);
	}
	
	public boolean addTextDescriptions(List<String> textDescriptions) {
		return this.textDescriptions.addAll(textDescriptions);		
	}

	public Set<Path> getImagePaths() {
		return this.imagePaths;
	}

	// public void setImagePaths(Set<Path> imagePaths) {
	// this.imagePaths = imagePaths;
	// }

	public boolean addImagePath(Path imagePath) {
		return this.imagePaths.add(imagePath);
	}
	
	public boolean addImagePaths(Set<Path> imagePaths) {
		return this.imagePaths.addAll(imagePaths);
	}

}
