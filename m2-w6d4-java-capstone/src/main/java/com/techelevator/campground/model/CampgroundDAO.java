package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {

		public List<Campground> getCampgroundsByParkID();
		
		public List<Campground> getCamproundInfo();
		
}
