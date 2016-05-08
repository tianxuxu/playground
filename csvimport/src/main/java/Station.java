import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.NullString;
import com.univocity.parsers.annotations.Parsed;

public class Station {

	@Parsed(field = "stn")
	private String code;

	@Parsed(field = "time")
	@Convert(conversionClass = ZonedDateTimeConversion.class)
	private ZonedDateTime dateTime;

	/**
	 * °C: Air temperature 2 m above ground; current value
	 */
	@Parsed(field = "tre200s0")
	@NullString(nulls = "-")
	private BigDecimal temperature;

	/**
	 * min: Sunshine duration; ten minutes total
	 */
	@Parsed(field = "sre000z0")
	@NullString(nulls = "-")
	private Integer sunshine;

	/**
	 * mm: Precipitation; ten minutes total
	 */
	@Parsed(field = "rre150z0")
	@NullString(nulls = "-")
	private BigDecimal precipitation;

	/**
	 * °: Wind direction; ten minutes mean
	 */
	@Parsed(field = "dkl010z0")
	@NullString(nulls = "-")
	private Integer windDirection;

	/**
	 * km/h: Wind speed; ten minutes mean
	 */
	@Parsed(field = "fu3010z0")
	@NullString(nulls = "-")
	private BigDecimal windSpeed;

	/**
	 * hPa: Pressure reduced to sea level according to standard atmosphere (QNH); current
	 * value
	 */
	@Parsed(field = "pp0qnhs0")
	@NullString(nulls = "-")
	private BigDecimal qnhPressure;

	/**
	 * km/h: Gust peak (one second); maximum
	 */
	@Parsed(field = "fu3010z1")
	@NullString(nulls = "-")
	private BigDecimal gustPeak;

	/**
	 * %: Relative air humidity 2 m above ground; current value
	 */
	@Parsed(field = "ure200s0")
	@NullString(nulls = "-")
	private Integer humidity;

	/**
	 * hPa: Pressure at station level (QFE); current value
	 */
	@Parsed(field = "prestas0")
	@NullString(nulls = "-")
	private BigDecimal qfePressure;

	/**
	 * hPa: Pressure reduced to sea level (QFF); current value
	 */
	@Parsed(field = "pp0qffs0")
	@NullString(nulls = "-")
	private BigDecimal qffPressure;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}

	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	public Integer getSunshine() {
		return sunshine;
	}

	public void setSunshine(Integer sunshine) {
		this.sunshine = sunshine;
	}

	public BigDecimal getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(BigDecimal precipitation) {
		this.precipitation = precipitation;
	}

	public Integer getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(Integer windDirection) {
		this.windDirection = windDirection;
	}

	public BigDecimal getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(BigDecimal windSpeed) {
		this.windSpeed = windSpeed;
	}

	public BigDecimal getQnhPressure() {
		return qnhPressure;
	}

	public void setQnhPressure(BigDecimal qnhPressure) {
		this.qnhPressure = qnhPressure;
	}

	public BigDecimal getGustPeak() {
		return gustPeak;
	}

	public void setGustPeak(BigDecimal gustPeak) {
		this.gustPeak = gustPeak;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public BigDecimal getQfePressure() {
		return qfePressure;
	}

	public void setQfePressure(BigDecimal qfePressure) {
		this.qfePressure = qfePressure;
	}

	public BigDecimal getQffPressure() {
		return qffPressure;
	}

	public void setQffPressure(BigDecimal qffPressure) {
		this.qffPressure = qffPressure;
	}

	@Override
	public String toString() {
		return "Station [code=" + code + ", dateTime=" + dateTime + ", temperature="
				+ temperature + ", sunshine=" + sunshine + ", precipitation="
				+ precipitation + ", windDirection=" + windDirection + ", windSpeed="
				+ windSpeed + ", qnhPressure=" + qnhPressure + ", gustPeak=" + gustPeak
				+ ", humidity=" + humidity + ", qfePressure=" + qfePressure
				+ ", qffPressure=" + qffPressure + "]";
	}

}
