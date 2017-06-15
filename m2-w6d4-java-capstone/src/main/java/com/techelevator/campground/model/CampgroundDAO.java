package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {

		public List<Campground> getCampgroundsByParkID(Long id);
		
		public List<Campground> getCamproundInfo(Long id);
		
}
