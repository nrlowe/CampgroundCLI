package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;


public interface ReservationDAO {
	public void createNewReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate);
	public List<String> confirmReservation(String name);
	

}
