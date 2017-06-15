package com.techelevator.campground.model;

import java.time.LocalDate;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String parkName;
	private LocalDate openFrom;
	private LocalDate openTo;
	private DollarAmount dailyFee;

	public Long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId() {
		this.campgroundId = campgroundId;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId() {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName() {
		this.parkName = parkName;
	}

	public LocalDate getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom() {
		this.openFrom = openFrom;
	}

	public LocalDate getOpenTo() {
		return openTo;
	}

	public void setOpenTo() {
		this.openTo = openTo;
	}

	public DollarAmount getDailyFee() {
		return dailyFee;
	}

	public void setDollarAmount() {
		this.dailyFee = dailyFee;
	}
}
