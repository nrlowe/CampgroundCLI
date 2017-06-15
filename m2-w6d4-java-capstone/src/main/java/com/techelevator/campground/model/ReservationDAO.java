package com.techelevator.campground.model;

import java.util.List;

public interface ReservationDAO {
	public List<Reservation> getAllReservations();
	public List<Reservation> searchReservationsByName();
	

}
