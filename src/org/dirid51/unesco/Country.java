package org.dirid51.unesco;

import java.util.HashSet;
import java.util.Set;

public class Country {
	private String name;
	private Set<HeritageSite> heritageSites;
	
	public Country() {
		heritageSites = new HashSet<>();
	}
	
	public Country(String name) {
		heritageSites = new HashSet<>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<HeritageSite> getHeritageSites() {
		return heritageSites;
	}

//	public void setHeritageSites(Set<HeritageSite> heritageSites) {
//		this.heritageSites = heritageSites;
//	}
	
	public boolean addHeritageSite(HeritageSite heritageSite) {
		return this.heritageSites.add(heritageSite);
	}

}
