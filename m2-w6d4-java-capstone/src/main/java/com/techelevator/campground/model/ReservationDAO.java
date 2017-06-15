package com.techelevator.campground.model;

import java.time.LocalDate;


public interface ReservationDAO {
	public void createNewReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate);
	public Reservation confirmReservation(String name);
	

}
