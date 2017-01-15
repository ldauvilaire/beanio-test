package net.ldauvilaire.beanio.tadast;

import java.util.List;

public class FlightLegGroup {

	private FlightLegRecord flightLeg;
	private List<PartnershipRecord> partnerships;

	public FlightLegRecord getFlightLeg() {
		return flightLeg;
	}
	public void setFlightLeg(FlightLegRecord flightLeg) {
		this.flightLeg = flightLeg;
	}

	public List<PartnershipRecord> getPartnerships() {
		return partnerships;
	}
	public void setPartnerships(List<PartnershipRecord> partnerships) {
		this.partnerships = partnerships;
	}
}
