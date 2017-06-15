package com.techelevator.campground.model;

import java.time.LocalDate;

public class Park {

	private Long parkId;
	private String parkName;
	private String location;
	private LocalDate establishDate;
	private Long area;
	private Long visitors;
	private String description;

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

	public String getLocation() {
		return location;
	}

	public void setLocation() {
		this.location = location;
	}

	public LocalDate getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate() {
		this.establishDate = establishDate;
	}

	public Long getArea() {
		return area;
	}

	public void setArea() {
		this.area = area;
	}

	public Long getVisitors() {
		return visitors;
	}

	public void setVisitors() {
		this.visitors = visitors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription() {
		this.description = description;
	}

}
