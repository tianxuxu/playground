package ch.rasc.forcastio.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The response to a forecat.io call
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FioResponse {

	private BigDecimal latitude;

	private BigDecimal longitude;

	private String timezone;

	private int offset;

	private FioDataPoint currently;

	private FioDataBlock minutely;

	private FioDataBlock hourly;

	private FioDataBlock daily;

	private List<FioAlert> alerts;

	private FioFlag flags;

	/**
	 * The requested latitude.
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * The requested longitude.
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}

	/**
	 * The IANA timezone name for the requested location (e.g. America/New_York). This is
	 * the timezone used for text forecast summaries and for determining the exact start
	 * time of daily data points.
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * The current timezone offset in hours from GMT.
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * A {@link FioDataPoint} object containing the current weather conditions at the
	 * requested location.
	 */
	public FioDataPoint getCurrently() {
		return currently;
	}

	/**
	 * A {@link FioDataPoint} object containing the weather conditions minute-by-minute
	 * for the next hour.
	 */
	public FioDataBlock getMinutely() {
		return minutely;
	}

	/**
	 * A {@link FioDataPoint} object containing the weather conditions hour-by-hour for
	 * the next two days.
	 */
	public FioDataBlock getHourly() {
		return hourly;
	}

	/**
	 * A {@link FioDataPoint} object containing the weather conditions day-by-day for the
	 * next week.
	 */
	public FioDataBlock getDaily() {
		return daily;
	}

	/**
	 * A collection of {@link FioAlert} instances, which, if present, contains any severe
	 * weather alerts, issued by a governmental weather authority, pertinent to the
	 * requested location.
	 */
	public List<FioAlert> getAlerts() {
		return alerts;
	}

	/**
	 * A {@link FioFlag} instance containing miscellaneous metadata concerning this
	 * request.
	 */
	public FioFlag getFlags() {
		return flags;
	}

	private void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	private void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	private void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	private void setOffset(int offset) {
		this.offset = offset;
	}

	private void setCurrently(FioDataPoint currently) {
		this.currently = currently;
	}

	private void setMinutely(FioDataBlock minutely) {
		this.minutely = minutely;
	}

	private void setHourly(FioDataBlock hourly) {
		this.hourly = hourly;
	}

	private void setDaily(FioDataBlock daily) {
		this.daily = daily;
	}

	private void setAlerts(List<FioAlert> alerts) {
		this.alerts = alerts;
	}

	private void setFlags(FioFlag flags) {
		this.flags = flags;
	}

	@Override
	public String toString() {
		return "FioResponse [latitude=" + latitude + ", longitude=" + longitude
				+ ", timezone=" + timezone + ", offset=" + offset + ", currently="
				+ currently + ", minutely=" + minutely + ", hourly=" + hourly
				+ ", daily=" + daily + ", alerts=" + alerts + ", flags=" + flags + "]";
	}

}
