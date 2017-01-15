package net.ldauvilaire.beanio.tadast;

import java.util.Date;

public class FlightLegRecord {

	private String type;
	private String departureStation;
	private Date departureDate;
	private String arrivalStation;
	private Date arrivalDate;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getDepartureStation() {
		return departureStation;
	}
	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
