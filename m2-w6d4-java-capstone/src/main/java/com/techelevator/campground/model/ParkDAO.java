package com.techelevator.campground.model;

import java.util.List;

public interface ParkDAO {

	public List<String> getAllParkNames();
	
	public List<Park> getAllParksByName();
	
	public Park getAllParkInfo(String name);

	
	
}
