package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {

		public List<Campground> getCampgroundsByParkName(String name);
		
		public List<Campground> getCamproundInfo(Long id);
		
}
