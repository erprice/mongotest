package mongotest;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

class MeterReading {

	private String id;
	private String meterId;
	private DateTime readingTime;
	private double kwh;

	@Id
	public void setId(String id) {
		this.id = id;
	}
	
	@Id
	public String getId() {
		return id;
	}
	
	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	@JsonIgnore
	public DateTime getReadingTime() {
		return readingTime;
	}
	
	@JsonIgnore
	public void setReadingTime(DateTime readingTime) {
		this.readingTime = readingTime;
	}
	
	@JsonProperty("readingTime")
	public long getReadingTimeMillis() {
		return readingTime.getMillis();
	}
	
	@JsonProperty("readingTime")
	public void setReadingTimeMillis(Long readingTimeMillis) {
		this.readingTime = new DateTime(readingTimeMillis);
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	@Override
	public String toString() {
		return "MeterReading [meterId=" + meterId + ", readingTime=" + readingTime + ", kwh=" + kwh + "]";
	}

}
