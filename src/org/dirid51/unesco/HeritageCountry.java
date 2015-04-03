package org.dirid51.unesco;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class HeritageCountry {
	private String name;
	private Set<HeritageSite> heritageSites;
	
	public HeritageCountry() {
		heritageSites = new TreeSet<>(new Comparator<HeritageSite>() {

			@Override
            public int compare(HeritageSite o1, HeritageSite o2) {
	            return o1.getName().compareToIgnoreCase(o2.getName());
            }
			
		});
	}
	
	public HeritageCountry(String name) {
		this();
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
