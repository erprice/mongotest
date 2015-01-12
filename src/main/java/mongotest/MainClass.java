package mongotest;

public class MainClass {

	public static void main(String args[]) {
		MetersData data = new MetersData();

		System.out.println("Generating and inserting test data");
		data.insertTestData();
		
		Meter meter = data.getAnyMeter();
		Double average = data.calculateAverageKwh(meter.getMeterId());
		System.out.println("Average kwh for all readings average=" + average);
		
		String meterId = data.getHighestKwhReading();
		Address address = data.getMeterAddress(meterId);
		System.out.println("Highest kwh reading address=" + address);
		
		MeterReading reading = data.getAnyMeterReading();
		double updatedKwh = 100.0;
		boolean wasSuccessful = data.updateReadingKwh(reading.getId(), updatedKwh);
		if (wasSuccessful) {
			System.out.println("Updated reading id=" + reading.getId() + " kwh=" + updatedKwh);
		} else {
			System.out.println("failed to update kwh for readingId=" + reading.getId());
		}
	}
	
}
