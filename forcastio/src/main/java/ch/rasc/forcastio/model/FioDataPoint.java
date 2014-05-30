package ch.rasc.forcastio.model;

import java.util.Date;

import ch.rasc.forcastio.converter.FioDataPointIconDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A data point object represents the various weather phenomena occurring at a
 * specific instant of time, and has many varied properties. All of these
 * properties (except time) are optional, and will only be set if we have that
 * type of information for that location and time. Please note that minutely
 * data points are always aligned to the nearest minute boundary, hourly points
 * to the top of the hour, and daily points to midnight of that day. Data points
 * in the daily data block (see below) are special: instead of representing the
 * weather phenomena at a given instant of time, they are an aggregate point
 * representing the weather phenomena that will occur over the entire day. For
 * precipitation fields, this aggregate is a maximum; for other fields, it is an
 * average.
 *
 */
public class FioDataPoint {

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at
	 * which this data point occurs.
	 */
	private Date time;

	/**
	 * A human-readable text summary of this data point.
	 */
	private String summary;

	/**
	 * A machine-readable text summary of this data point, suitable for
	 * selecting an icon for display. If defined, this property will have one of
	 * the following values: clear-day, clear-night, rain, snow, sleet, wind,
	 * fog, cloudy, partly-cloudy-day, or partly-cloudy-night. (Developers
	 * should ensure that a sensible default is defined, as additional values,
	 * such as hail, thunderstorm, or tornado, may be defined in the future.)
	 */
	@JsonDeserialize(using = FioDataPointIconDeserializer.class)
	private FioDataPointIcon icon;

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) of
	 * sunrise on the given day. (If no sunrise will occur on the given day,
	 * then the appropriate fields will be undefined. This can occur during
	 * summer and winter in very high or low latitudes.) (only defined on daily
	 * data points)
	 */
	private Date sunriseTime;

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) of
	 * sunset on the given day. (If no sunset will occur on the given day, then
	 * the appropriate fields will be undefined. This can occur during summer
	 * and winter in very high or low latitudes.) (only defined on daily data
	 * points)
	 */

	private Date sunsetTime;

	/**
	 * A numerical value representing the average expected intensity (in inches
	 * of liquid water per hour) of precipitation occurring at the given time
	 * conditional on probability (that is, assuming any precipitation occurs at
	 * all). A very rough guide is that a value of 0 corresponds to no
	 * precipitation, 0.002 corresponds to very light precipitation, 0.017
	 * corresponds to light precipitation, 0.1 corresponds to moderate
	 * precipitation, and 0.4 corresponds to very heavy precipitation.
	 */
	private Double precipIntensity;

	private Double precipIntensityError;

	/**
	 * numerical values representing the maximumum expected intensity of
	 * precipitation (and the UNIX time at which it occurs) on the given day in
	 * inches of liquid water per hour. (only defined on daily data points)
	 */
	private Double precipIntensityMax;

	private Date precipIntensityMaxTime;

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the
	 * probability of precipitation occuring at the given time. (If
	 * precipIntensity is zero, then this property would be redundant and will
	 * therefore not be defined.)
	 */
	private Double precipProbability;

	/**
	 * A string representing the type of precipitation occurring at the given
	 * time. If defined, this property will have one of the following values:
	 * rain, snow, sleet (which applies to each of freezing rain, ice pellets,
	 * and “wintery mix”), or hail. (If precipIntensity is zero, then this
	 * property will not be defined.)
	 */
	private String precipType;

	/*
	 * (only defined on daily data points): the amount of snowfall accumulation
	 * expected to occur on the given day. (If no accumulation is expected, this
	 * property will not be defined.)
	 */
	private Double precipAccumulation;

	/**
	 * (not defined on daily data points): A numerical value representing the
	 * temperature at the given time in degrees Fahrenheit.
	 */
	private Double temperature;

	/**
	 * (only defined on daily data points): numerical values representing the
	 * minimum and maximumum temperatures (and the UNIX times at which they
	 * occur) on the given day in degrees Fahrenheit.
	 */
	private Double temperatureMin;

	private Date temperatureMinTime;

	private Double temperatureMax;

	private Date temperatureMaxTime;

	/**
	 * A numerical value representing the dew point at the given time in degrees
	 * Fahrenheit.
	 */
	private Double dewPoint;

	/**
	 * A numerical value representing the wind speed in miles per hour.
	 */
	private Double windSpeed;

	private Double windSpeedError;

	/**
	 * A numerical value representing the direction that the wind is coming from
	 * in degrees, with true north at 0° and progressing clockwise. (If
	 * windSpeed is zero, then this value will not be defined.)
	 */
	private Double windBearing;

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the percentage
	 * of sky occluded by clouds. A value of 0 corresponds to clear sky, 0.4 to
	 * scattered clouds, 0.75 to broken cloud cover, and 1 to completely
	 * overcast skies.
	 */
	private Double cloudCover;

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the relative
	 * humidity.
	 */
	private Double humidity;

	/**
	 * A numerical value representing the sea-level air pressure in millibars.
	 */
	private Double pressure;

	private Double pressureError;

	/**
	 * A numerical value representing the average visibility in miles, capped at
	 * 10 miles.
	 */
	private Double visibility;

	/**
	 * A numerical value representing the columnar density of total atmospheric
	 * ozone at the given time in Dobson units.
	 */
	private Double ozone;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public FioDataPointIcon getIcon() {
		return icon;
	}

	public void setIcon(FioDataPointIcon icon) {
		this.icon = icon;
	}

	public Date getSunriseTime() {
		return sunriseTime;
	}

	public void setSunriseTime(Date sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	public Date getSunsetTime() {
		return sunsetTime;
	}

	public void setSunsetTime(Date sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	public Double getPrecipIntensity() {
		return precipIntensity;
	}

	public void setPrecipIntensity(Double precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	public Double getPrecipIntensityMax() {
		return precipIntensityMax;
	}

	public void setPrecipIntensityMax(Double precipIntensityMax) {
		this.precipIntensityMax = precipIntensityMax;
	}

	public Date getPrecipIntensityMaxTime() {
		return precipIntensityMaxTime;
	}

	public void setPrecipIntensityMaxTime(Date precipIntensityMaxTime) {
		this.precipIntensityMaxTime = precipIntensityMaxTime;
	}

	public Double getPrecipProbability() {
		return precipProbability;
	}

	public void setPrecipProbability(Double precipProbability) {
		this.precipProbability = precipProbability;
	}

	public String getPrecipType() {
		return precipType;
	}

	public void setPrecipType(String precipType) {
		this.precipType = precipType;
	}

	public Double getPrecipAccumulation() {
		return precipAccumulation;
	}

	public void setPrecipAccumulation(Double precipAccumulation) {
		this.precipAccumulation = precipAccumulation;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(Double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public Date getTemperatureMinTime() {
		return temperatureMinTime;
	}

	public void setTemperatureMinTime(Date temperatureMinTime) {
		this.temperatureMinTime = temperatureMinTime;
	}

	public Double getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(Double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public Date getTemperatureMaxTime() {
		return temperatureMaxTime;
	}

	public void setTemperatureMaxTime(Date temperatureMaxTime) {
		this.temperatureMaxTime = temperatureMaxTime;
	}

	public Double getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(Double dewPoint) {
		this.dewPoint = dewPoint;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Double getWindBearing() {
		return windBearing;
	}

	public void setWindBearing(Double windBearing) {
		this.windBearing = windBearing;
	}

	public Double getCloudCover() {
		return cloudCover;
	}

	public void setCloudCover(Double cloudCover) {
		this.cloudCover = cloudCover;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Double getVisibility() {
		return visibility;
	}

	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}

	public Double getOzone() {
		return ozone;
	}

	public void setOzone(Double ozone) {
		this.ozone = ozone;
	}

	public Double getPrecipIntensityError() {
		return precipIntensityError;
	}

	public void setPrecipIntensityError(Double precipIntensityError) {
		this.precipIntensityError = precipIntensityError;
	}

	public Double getWindSpeedError() {
		return windSpeedError;
	}

	public void setWindSpeedError(Double windSpeedError) {
		this.windSpeedError = windSpeedError;
	}

	public Double getPressureError() {
		return pressureError;
	}

	public void setPressureError(Double pressureError) {
		this.pressureError = pressureError;
	}

	@Override
	public String toString() {
		return "FioDataPoint [time=" + time + ", summary=" + summary
				+ ", icon=" + icon + ", sunriseTime=" + sunriseTime
				+ ", sunsetTime=" + sunsetTime + ", precipIntensity="
				+ precipIntensity + ", precipIntensityError="
				+ precipIntensityError + ", precipIntensityMax="
				+ precipIntensityMax + ", precipIntensityMaxTime="
				+ precipIntensityMaxTime + ", precipProbability="
				+ precipProbability + ", precipType=" + precipType
				+ ", precipAccumulation=" + precipAccumulation
				+ ", temperature=" + temperature + ", temperatureMin="
				+ temperatureMin + ", temperatureMinTime=" + temperatureMinTime
				+ ", temperatureMax=" + temperatureMax
				+ ", temperatureMaxTime=" + temperatureMaxTime + ", dewPoint="
				+ dewPoint + ", windSpeed=" + windSpeed + ", windSpeedError="
				+ windSpeedError + ", windBearing=" + windBearing
				+ ", cloudCover=" + cloudCover + ", humidity=" + humidity
				+ ", pressure=" + pressure + ", pressureError=" + pressureError
				+ ", visibility=" + visibility + ", ozone=" + ozone + "]";
	}

}
