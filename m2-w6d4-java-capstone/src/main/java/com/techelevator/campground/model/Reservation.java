package com.techelevator.campground.model;

import java.time.LocalDate;

public class Reservation {
	private Long reservationId;
	private Long siteId;
	private String name;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate reservationCreatedDate;
	
	public Long getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	
	public Long getSiteId() {
		return siteId;
	}
	
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	
	public LocalDate getToDate() {
		return toDate;
	}
	
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	public LocalDate getReservationCreatedDate() {
		return reservationCreatedDate;
	}
	
	public void setReservationCreatedDate(LocalDate reservationCreatedDate) {
		this.reservationCreatedDate = reservationCreatedDate;;
	}
	
}
















