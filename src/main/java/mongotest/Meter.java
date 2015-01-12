package mongotest;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Meter {
	
	private String meterId;
	private String meterModel;
	private DateTime installDate;
	private Address address;

	@Id
	public String getMeterId() {
		return meterId;
	}

	@Id
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getMeterModel() {
		return meterModel;
	}

	public void setMeterModel(String meterModel) {
		this.meterModel = meterModel;
	}

	@JsonIgnore
	public DateTime getInstallDate() {
		return installDate;
	}
	
	@JsonIgnore
	public void setInstallDate(DateTime installDate) {
		this.installDate = installDate;
	}

	@JsonProperty("installDate")
	public long getInstallDateMillis() {
		return installDate.getMillis();
	}
	
	@JsonProperty("installDate")
	public void setInstallDateMillis(Long installDateMillis) {
		this.installDate = new DateTime(installDateMillis);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Meter [meterId=" + meterId + ", meterModel=" + meterModel + ", installDate=" + installDate
				+ ", address=" + address + "]";
	}
}
