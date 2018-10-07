package abc.xyz.pts.bcs.common.controller.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import abc.xyz.pts.bcs.common.controller.util.ControllerUtil;
import abc.xyz.pts.bcs.common.db.dto.FlightSearch;


public class TestControllerUtil {

	private class DummyFlightSearch implements FlightSearch {
	    private String departureAirport;
	    private String arrivalAirport;
	    private String defaultDateRangeFrom;
	    private String defaultDateRangeTo;

	    public String getDepartureAirport() {
	        return departureAirport;
	    }
	    public void setDepartureAirport(final String value) {
	        this.departureAirport = value;
	    }
	    public String getArrivalAirport() {
	        return arrivalAirport;
	    }
	    public void setArrivalAirport(final String value) {
	        this.arrivalAirport = value;
	    }
		public String getDefaultDateRangeFrom() {
			return defaultDateRangeFrom;
		}
		public void setDefaultDateRangeFrom(final String defaultDateRangeFrom) {
			this.defaultDateRangeFrom = defaultDateRangeFrom;
		}
		public String getDefaultDateRangeTo() {
			return defaultDateRangeTo;
		}
		public void setDefaultDateRangeTo(final String defaultDateRangeTo) {
			this.defaultDateRangeTo = defaultDateRangeTo;
		}

	}
	
	@Test
	public void testSetWorkingAirport() throws Exception {
		
		final String WORKING_AIRPORT = "WA";
		
		// nothing set
		DummyFlightSearch searchCommand = new DummyFlightSearch();
		ControllerUtil.setWorkingAirport(searchCommand, WORKING_AIRPORT);
		assertNull(searchCommand.getArrivalAirport());
		assertNull(searchCommand.getDepartureAirport());
		
		// departue set
		searchCommand = new DummyFlightSearch();
		searchCommand.setArrivalAirport("ARR");
		ControllerUtil.setWorkingAirport(searchCommand, WORKING_AIRPORT);
		assertEquals(WORKING_AIRPORT, searchCommand.getDepartureAirport());
		assertEquals("ARR", searchCommand.getArrivalAirport());
		
		// arrival set
		searchCommand = new DummyFlightSearch();
		searchCommand.setDepartureAirport("DEP");
		ControllerUtil.setWorkingAirport(searchCommand, WORKING_AIRPORT);
		assertEquals(WORKING_AIRPORT, searchCommand.getArrivalAirport());
		assertEquals("DEP", searchCommand.getDepartureAirport());
		
		// no working airport, nothing set
		searchCommand = new DummyFlightSearch();
		searchCommand.setArrivalAirport("ARR");
		searchCommand.setDepartureAirport("DEP");
		ControllerUtil.setWorkingAirport(searchCommand, null);
		assertEquals("DEP", searchCommand.getDepartureAirport());
		assertEquals("ARR", searchCommand.getArrivalAirport());
		
		// arr set to working airport
		searchCommand = new DummyFlightSearch(); 
		searchCommand.setArrivalAirport(WORKING_AIRPORT);
		ControllerUtil.setWorkingAirport(searchCommand, null);
		assertNull(searchCommand.getDepartureAirport());
		assertEquals(WORKING_AIRPORT, searchCommand.getArrivalAirport());
		
		// dep set to working airport
		searchCommand = new DummyFlightSearch(); 
		searchCommand.setDepartureAirport(WORKING_AIRPORT);
		ControllerUtil.setWorkingAirport(searchCommand, null);
		assertNull(searchCommand.getArrivalAirport());
		assertEquals(WORKING_AIRPORT, searchCommand.getDepartureAirport());
		
	}
}
