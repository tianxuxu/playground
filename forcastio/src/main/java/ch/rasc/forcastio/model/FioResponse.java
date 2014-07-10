package ch.rasc.forcastio.model;

import java.util.List;

public class FioResponse {

	/**
	 * The requested latitude.
	 */
	private double latitude;

	/**
	 * The requested longitude.
	 */
	private double longitude;

	/**
	 * The IANA timezone name for the requested location
	 */
	private String timezone;

	/**
	 * The current timezone offset in hours from GMT.
	 */
	private int offset;

	/**
	 * A data point containing the current weather conditions at the requested location.
	 */
	private FioDataPoint currently;

	/**
	 * A data block containing the weather conditions minute-by-minute for the next hour.
	 * (This property’s name should be read as an adjective—analogously to "hourly" or
	 * "daily" and meaning "reckoned by the minute"—rather than as an adverb meaning
	 * "meticulously."
	 */
	private FioDataBlock minutely;

	/**
	 * A data block (see below) containing the weather conditions hour-by-hour for the
	 * next two days.
	 */
	private FioDataBlock hourly;

	/**
	 * A data block containing the weather conditions day-by-day for the next week.
	 */
	private FioDataBlock daily;

	/**
	 * An array of alert objects, which, if present, contains any severe weather alerts,
	 * issued by a governmental weather authority, pertinent to the requested location.
	 */
	private List<FioAlert> alerts;

	/**
	 * An object containing miscellaneous metadata concerning this request.
	 */
	private FioFlag flags;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public FioDataPoint getCurrently() {
		return currently;
	}

	public void setCurrently(FioDataPoint currently) {
		this.currently = currently;
	}

	public FioDataBlock getMinutely() {
		return minutely;
	}

	public void setMinutely(FioDataBlock minutely) {
		this.minutely = minutely;
	}

	public FioDataBlock getHourly() {
		return hourly;
	}

	public void setHourly(FioDataBlock hourly) {
		this.hourly = hourly;
	}

	public FioDataBlock getDaily() {
		return daily;
	}

	public void setDaily(FioDataBlock daily) {
		this.daily = daily;
	}

	public List<FioAlert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<FioAlert> alerts) {
		this.alerts = alerts;
	}

	public FioFlag getFlags() {
		return flags;
	}

	public void setFlags(FioFlag flags) {
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
