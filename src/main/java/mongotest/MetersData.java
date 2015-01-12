package mongotest;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * Wrapper class for mongoDB operations on the meters database.
 */
public class MetersData {
	
	private final static String METERS_DB = "meters";

	private final static String COLL_METERS = "meters";
	private final static String COLL_READINGS = "readings";

	MongoClient mongoClient;
	DB db;

	private JacksonDBCollection<Meter, String> metersColl;
	private JacksonDBCollection<MeterReading, String> readingsColl;

	public MetersData() {
		try {
			mongoClient = new MongoClient();
		} catch (UnknownHostException e) {
			System.err.println("Could not detect a mongoDB instance!");
			e.printStackTrace();
			System.exit(1);
		}

		db = mongoClient.getDB(METERS_DB);
		metersColl = JacksonDBCollection.wrap(db.getCollection(COLL_METERS), Meter.class, String.class);
		readingsColl = JacksonDBCollection.wrap(db.getCollection(COLL_READINGS), MeterReading.class, String.class);
	}

	/**
	 * Insert 10 test data meters, each with 24 test data meterReadings.
	 */
	public void insertTestData() {
		for (Meter meter : DataGenerator.getMeters()) {
			// we need to individually insert each Meter to get the object ID from mongo
			Meter insertionResult = insertMeter(meter);
			
			List<MeterReading> readings = DataGenerator.getReadings(insertionResult.getMeterId());

			// we can insert readings in batch b/c we aren't trying to capture the object ID
			readingsColl.insert(readings);
		}
	}

	public Meter insertMeter(Meter meter) {
		WriteResult<Meter, String> writeResult = metersColl.insert(meter);
		return writeResult.getSavedObject();
	}

	public MeterReading insertMeterReading(MeterReading reading) {
		WriteResult<MeterReading, String> writeResult = readingsColl.insert(reading);
		return writeResult.getSavedObject();
	}
	
	public Meter getAnyMeter() {
		return metersColl.findOne();
	}

	public MeterReading getAnyMeterReading() {
		return readingsColl.findOne();
	}
	
	/**
	 * Find the average kilowatt hour value across all meter readings for one meter
	 * 
	 * @param meterId
	 * @return average killowatt hour reading
	 */
	public Double calculateAverageKwh(String meterId) {
		// Do not use MongoJack here because it apparently has deprecated aggregation...?
		
		// build the DB operations to match and group results
		DBObject match = new BasicDBObject("$match", new BasicDBObject("meterId", meterId));
		DBObject groupFields = new BasicDBObject("_id", "$meterId");
		DBObject averageKwh = new BasicDBObject("$avg", "$kwh");
		groupFields.put("average", averageKwh);
		DBObject group = new BasicDBObject("$group", groupFields);

		DBCollection collection = db.getCollection(COLL_READINGS);
		AggregationOutput output = collection.aggregate(match, group);

		//get the kwh average from the results
		BasicBSONList resultList = (BasicBSONList) output.getCommandResult().get("result");
		BasicDBObject result = (BasicDBObject) resultList.get(0);
		
		return result.getDouble("average");
	}

	public Address getMeterAddress(String meterId) {
		BasicDBObject findById = new BasicDBObject("_id", new ObjectId(meterId));
		DBCursor<Meter> cursor = metersColl.find(findById);
		Meter meter = cursor.next();

		return meter.getAddress();
	}

	/**
	 * Find the meterId of the meter with the highest kilowatt hour reading of all readings.
	 * @return meterId
	 */
	public String getHighestKwhReading() {
		BasicDBObject kwhSort = new BasicDBObject("kwh", -1);
		DBCursor<MeterReading> cursor = readingsColl.find().sort(kwhSort).limit(1);
		if (cursor.hasNext()) {
			MeterReading reading = cursor.next();
			if (reading != null) {
				return reading.getMeterId();
			}
		}

		return null;
	}

	/**
	 * Update the kilowatt hour reading of one MeterReading.
	 * 
	 * @param readingId
	 * @param kwh
	 * @return true if successful, false otherwise
	 */
	public boolean updateReadingKwh(String readingId, Double kwh) {
		//MongoJack is apparently also broken on update by ID.
		//https://github.com/devbliss/mongojack/issues/15
		//In retrospect, maybe it was not the best library to use...
		BasicDBObject queryObject = new BasicDBObject("_id", new ObjectId(readingId));
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.append("$set", new BasicDBObject().append("kwh", kwh));
		
		com.mongodb.WriteResult result = db.getCollection(COLL_READINGS).update(queryObject, updateObject);
		
		if (result != null) {
			if (result.getField("updatedExisting") != null) {
				return (Boolean)result.getField("updatedExisting");
			}			
		}
		
		return false;
	}

}
