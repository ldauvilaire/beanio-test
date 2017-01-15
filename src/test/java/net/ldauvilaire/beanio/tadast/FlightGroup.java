package net.ldauvilaire.beanio.tadast;

import java.util.List;

public class FlightGroup {

	private FlightRecord flight;
	private List<FlightLegGroup> flightLegs;

	public FlightRecord getFlight() {
		return flight;
	}
	public void setFlight(FlightRecord flight) {
		this.flight = flight;
	}

	public List<FlightLegGroup> getFlightLegs() {
		return flightLegs;
	}
	public void setFlightLegs(List<FlightLegGroup> flightLegs) {
		this.flightLegs = flightLegs;
	}

}
