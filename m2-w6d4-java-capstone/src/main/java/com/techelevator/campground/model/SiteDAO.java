package com.techelevator.campground.model;

import java.util.List;

public interface SiteDAO {
	
	public List<Site> findSite();
	public List<Site> findSiteByCampground();

}
