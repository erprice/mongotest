package mongotest;
public class Address {
	
	private String line;
	private String city;
	private State state;
	private String zipCode;
	
	public Address(String line, String city, State state, String zipCode) {
		this.line = line;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	/*
	 * Blank constructor for mongo to instantiate Address objects
	 */
	public Address() {
	}
	
	public void setLine(String line) {
		this.line = line;
	}
	
	public String getLine() {
		return line;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getZipCode() {
		return zipCode;
	}

	@Override
	public String toString() {
		return "Address [line=" + line + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
}
