package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> getCurrentReservationsBySite(Long id, LocalDate fromDate, LocalDate toDate);

}
