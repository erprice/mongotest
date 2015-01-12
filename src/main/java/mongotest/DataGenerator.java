package mongotest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * Generates some silly test data for use in reading/writing to MongoDB.  
 */
public class DataGenerator {

	//mutable static collections cause memory leaks.
	//TODO Convert these to non-static, or better yet, randomized values
	public final static List<String> CITIES;
	public final static List<String> ADDRESS_LINE;
	public final static List<State> STATES;
	
	private final static Random r = new Random();
	
	static {
		CITIES = new ArrayList<String>(10);
		CITIES.add("San Francisco");
		CITIES.add("Seoul");
		CITIES.add("Chattanooga");
		CITIES.add("Timbuktu");
		CITIES.add("Helsinki");
		CITIES.add("Lima");
		CITIES.add("Darwin");
		CITIES.add("Kyoto");
		CITIES.add("Xi'an");
		CITIES.add("Nairobi");
		
		ADDRESS_LINE = new ArrayList<String>(10);
		ADDRESS_LINE.add("123 Sesame Street");
		ADDRESS_LINE.add("1600 Pennsylvania Avenue");
		ADDRESS_LINE.add("4700 Falls of Neuse Road #340");
		ADDRESS_LINE.add("2 Macquarie Street");
		ADDRESS_LINE.add("742 Evergreen Terrace");
		ADDRESS_LINE.add("110 N. Moore Street");
		ADDRESS_LINE.add("Alnwick Castle");
		ADDRESS_LINE.add("101 St. Nicholas Drive");
		ADDRESS_LINE.add("One LEGOLAND Drive");
		ADDRESS_LINE.add("1 Electric Avenue");
		
		STATES = new ArrayList<State>(10);
		STATES.add(State.INDIANA);
		STATES.add(State.VIRGINIA);
		STATES.add(State.WASHINGTON);
		STATES.add(State.ILLINOIS);
		STATES.add(State.ALABAMA);
		STATES.add(State.PUERTO_RICO);
		STATES.add(State.MARYLAND);
		STATES.add(State.HAWAII);
		STATES.add(State.COLORADO);
		STATES.add(State.NORTH_CAROLINA);
	}
	
	private static String getRandomZipCode() {
		Integer zipCodeInt = (int) (r.nextDouble() * 100000);
		return Integer.toString(zipCodeInt);
	}
	
	private static DateTime getRandomDateTimeBeforeNow() {
		return new DateTime().minusMillis(r.nextInt());
	}
	
	private static List<Address> getListOfAddresses() {
		List<Address> addresses = new ArrayList<Address>(10);
		for (int i = 0; i < 10; i++) {
			addresses.add(new Address(ADDRESS_LINE.get(i), CITIES.get(i), STATES.get(i), getRandomZipCode()));
		}
		
		return addresses;
	}
	
	/**
	 * Return a list of 10 meters with test data
	 */
	public static List<Meter> getMeters() {
		List<Meter> meters = new ArrayList<Meter>(10);
		List<Address> addresses = getListOfAddresses();
		
		//generate 10 meters
		for (Address address : addresses) {
			Meter meter = new Meter();
			meter.setAddress(address);
			meter.setMeterModel("meterModel-" + UUID.randomUUID().toString());
			meter.setInstallDate(getRandomDateTimeBeforeNow());
			meters.add(meter);
		}
		
		return meters;
	}
	
	/**
	 * Return a list of random readings for the past 24 hours for this meter
	 */
	public static List<MeterReading> getReadings(String meterId) {
		List<MeterReading> readings = new ArrayList<MeterReading>(24);
		
		//generate 24 meter readings for each meter
		DateTime now = new DateTime();
		for (int j = 0; j < 24; j++) {
			MeterReading reading = new MeterReading();
			reading.setKwh(r.nextDouble());
			reading.setMeterId(meterId);
			reading.setReadingTime(now.minusHours(j));
			readings.add(reading);
		}
		
		return readings;
	}
	
}
