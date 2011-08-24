package swinghacks.ch12.Miscellany.hack98;

import java.math.BigDecimal;

public class Weather {

	private BigDecimal temperature;
	private BigDecimal humidity;
	private BigDecimal pressure;
	private String day;

	public Weather(BigDecimal temperature, BigDecimal humidity, BigDecimal pressure, String day) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.day = day;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}

	public BigDecimal getHumidity() {
		return humidity;
	}

	public BigDecimal getPressure() {
		return pressure;
	}

	public String getDay() {
		return day;
	}

}
