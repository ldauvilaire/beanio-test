package net.ldauvilaire.beanio.tadast;

import java.util.Date;

public class FlightRecord {

	private String type;
	private String airlineCode;
	private String flightNumber;
	private Date flightIdentifierDate;
	private String operationalSuffix;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Date getFlightIdentifierDate() {
		return flightIdentifierDate;
	}
	public void setFlightIdentifierDate(Date flightIdentifierDate) {
		this.flightIdentifierDate = flightIdentifierDate;
	}

	public String getOperationalSuffix() {
		return operationalSuffix;
	}
	public void setOperationalSuffix(String operationalSuffix) {
		this.operationalSuffix = operationalSuffix;
	}
}
