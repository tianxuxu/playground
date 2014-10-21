package ch.rasc.forcastio.model;

import java.math.BigDecimal;

import ch.rasc.forcastio.converter.FioIconDeserializer;
import ch.rasc.forcastio.converter.FioPrecipTypeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A data point object represents the various weather phenomena occurring at a specific
 * instant of time, and has many varied properties. All of these properties (except time)
 * are optional, and will only be set if we have that type of information for that
 * location and time. Please note that minutely data points are always aligned to the
 * nearest minute boundary, hourly points to the top of the hour, and daily points to
 * midnight of that day.
 *
 * Data points in the daily data block are special: instead of representing the weather
 * phenomena at a given instant of time, they are an aggregate point representing the
 * weather phenomena that will occur over the entire day. For precipitation fields, this
 * aggregate is a maximum; for other fields, it is an average.
 */
@SuppressWarnings("unused")
public class FioDataPoint {

	private Integer time;

	private String summary;

	@JsonDeserialize(using = FioIconDeserializer.class)
	private FioIcon icon;

	private Integer sunriseTime;

	private Integer sunsetTime;

	private BigDecimal moonPhase;

	private BigDecimal nearestStormDistance;

	private BigDecimal nearestStormBearing;

	private BigDecimal precipIntensity;

	private BigDecimal precipIntensityMax;

	private Integer precipIntensityMaxTime;

	private BigDecimal precipProbability;

	@JsonDeserialize(using = FioPrecipTypeDeserializer.class)
	private FioPrecipType precipType;

	private BigDecimal precipAccumulation;

	private BigDecimal temperature;

	private BigDecimal temperatureMin;

	private Integer temperatureMinTime;

	private BigDecimal temperatureMax;

	private Integer temperatureMaxTime;

	private BigDecimal apparentTemperature;

	private BigDecimal apparentTemperatureMin;

	private Integer apparentTemperatureMinTime;

	private BigDecimal apparentTemperatureMax;

	private Integer apparentTemperatureMaxTime;

	private BigDecimal dewPoint;

	private BigDecimal windSpeed;

	private BigDecimal windBearing;

	private BigDecimal cloudCover;

	private BigDecimal humidity;

	private BigDecimal pressure;

	private BigDecimal visibility;

	private BigDecimal ozone;

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which this
	 * data point occurs.
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * A human-readable text summary of this data point.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * A machine-readable text summary of this data point, suitable for selecting an icon
	 * for display.
	 */
	public FioIcon getIcon() {
		return icon;
	}

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) of the last
	 * sunrise before the solar noon closest to local noon on the given day.
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getSunriseTime() {
		return sunriseTime;
	}

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) of the first
	 * sunset after the solar noon closest to local noon on the given day.
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getSunsetTime() {
		return sunsetTime;
	}

	/**
	 * A number representing the fractional part of the lunation number of the given day.
	 * This can be thought of as the "percentage complete" of the current lunar month: a
	 * value of 0 represents a new moon, a value of 0.25 represents a first quarter moon,
	 * a value of 0.5 represents a full moon, and a value of 0.75 represents a last
	 * quarter moon. (The ranges in between these represent waxing crescent, waxing
	 * gibbous, waning gibbous, and waning crescent moons, respectively.)
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getMoonPhase() {
		return moonPhase;
	}

	/**
	 * A numerical value representing the distance to the nearest storm in miles. (This
	 * value is very approximate and should not be used in scenarios requiring accurate
	 * results. In particular, a storm distance of zero doesn’t necessarily refer to a
	 * storm at the requested location, but rather a storm in the vicinity of that
	 * location.)
	 * <p>
	 * Only defined on currently data points.
	 */
	public BigDecimal getNearestStormDistance() {
		return nearestStormDistance;
	}

	/**
	 * A numerical value representing the direction of the nearest storm in degrees, with
	 * true north at 0° and progressing clockwise. (If nearestStormDistance is zero, then
	 * this value will not be defined. The caveats that apply to nearestStormDistance also
	 * apply to this value.)
	 * <p>
	 * Only defined on currently data points.
	 */
	public BigDecimal getNearestStormBearing() {
		return nearestStormBearing;
	}

	/**
	 * A numerical value representing the average expected intensity (in inches of liquid
	 * water per hour) of precipitation occurring at the given time conditional on
	 * probability (that is, assuming any precipitation occurs at all). A very rough guide
	 * is that a value of 0 in./hr. corresponds to no precipitation, 0.002 in./hr.
	 * corresponds to very light precipitation, 0.017 in./hr. corresponds to light
	 * precipitation, 0.1 in./hr. corresponds to moderate precipitation, and 0.4 in./hr.
	 * corresponds to heavy precipitation.
	 */
	public BigDecimal getPrecipIntensity() {
		return precipIntensity;
	}

	/**
	 * A numerical value representing the maximumum expected intensity of precipitation in
	 * inches of liquid water per hour.
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getPrecipIntensityMax() {
		return precipIntensityMax;
	}

	/**
	 * The UNIX time at which {@link #getPrecipIntensityMax()} occurs on the given day
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getPrecipIntensityMaxTime() {
		return precipIntensityMaxTime;
	}

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the probability of
	 * precipitation occuring at the given time.
	 */
	public BigDecimal getPrecipProbability() {
		return precipProbability;
	}

	/**
	 * A value representing the type of precipitation occurring at the given time. If
	 * defined, this property will have one of the following values:
	 * {@link FioPrecipType#RAIN}, {@link FioPrecipType#SNOW}, {@link FioPrecipType#SLEET}
	 * (which applies to each of freezing rain, ice pellets, and "wintery mix"), or
	 * {@link FioPrecipType#HAIL}.
	 * <p>
	 * If {@link #getPrecipIntensity()} is zero, then this property will not be defined.
	 */
	public FioPrecipType getPrecipType() {
		return precipType;
	}

	/**
	 * The amount of snowfall accumulation expected to occur on the given day. (If no
	 * accumulation is expected, this property will not be defined.)
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getPrecipAccumulation() {
		return precipAccumulation;
	}

	/**
	 * A numerical value representing the temperature at the given time in degrees
	 * Fahrenheit.
	 * <p>
	 * Not defined on daily data points.
	 */
	public BigDecimal getTemperature() {
		return temperature;
	}

	/**
	 * Numerical values representing the minimum temperature on the given day in degrees
	 * Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getTemperatureMin() {
		return temperatureMin;
	}

	/**
	 * The UNIX time at which {@link #getTemperatureMin()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getTemperatureMinTime() {
		return temperatureMinTime;
	}

	/**
	 * A numerical value representing the maximumum temperature on the given day in
	 * degrees Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getTemperatureMax() {
		return temperatureMax;
	}

	/**
	 * The UNIX time at which {@link #getTemperatureMax()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getTemperatureMaxTime() {
		return temperatureMaxTime;
	}

	/**
	 * A numerical value representing the apparent (or "feels like") temperature at the
	 * given time in degrees Fahrenheit.
	 * <p>
	 * Not defined on daily data points.
	 */
	public BigDecimal getApparentTemperature() {
		return apparentTemperature;
	}

	/**
	 * A numerical value representing the minimum temperature on the given day in degrees
	 * Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getApparentTemperatureMin() {
		return apparentTemperatureMin;
	}

	/**
	 * The UNIX time at which {@link #getApparentTemperatureMin()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getApparentTemperatureMinTime() {
		return apparentTemperatureMinTime;
	}

	/**
	 * A numerical value representing the maximumum apparent temperature on the given day
	 * in degrees Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	public BigDecimal getApparentTemperatureMax() {
		return apparentTemperatureMax;
	}

	/**
	 * The UNIX time at which {@link #getApparentTemperatureMax()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	public Integer getApparentTemperatureMaxTime() {
		return apparentTemperatureMaxTime;
	}

	/**
	 * A numerical value representing the dew point at the given time in degrees
	 * Fahrenheit.
	 */
	public BigDecimal getDewPoint() {
		return dewPoint;
	}

	/**
	 * A numerical value representing the wind speed in miles per hour.
	 */
	public BigDecimal getWindSpeed() {
		return windSpeed;
	}

	/**
	 * A numerical value representing the direction that the wind is coming from in
	 * degrees, with true north at 0° and progressing clockwise. (If windSpeed is zero,
	 * then this value will not be defined.)
	 */
	public BigDecimal getWindBearing() {
		return windBearing;
	}

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the percentage of sky
	 * occluded by clouds. A value of 0 corresponds to clear sky, 0.4 to scattered clouds,
	 * 0.75 to broken cloud cover, and 1 to completely overcast skies.
	 */
	public BigDecimal getCloudCover() {
		return cloudCover;
	}

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the relative humidity.
	 */
	public BigDecimal getHumidity() {
		return humidity;
	}

	/**
	 * A numerical value representing the sea-level air pressure in millibars.
	 */
	public BigDecimal getPressure() {
		return pressure;
	}

	/**
	 * A numerical value representing the average visibility in miles, capped at 10 miles.
	 */
	public BigDecimal getVisibility() {
		return visibility;
	}

	/**
	 * A numerical value representing the columnar density of total atmospheric ozone at
	 * the given time in Dobson units.
	 */
	public BigDecimal getOzone() {
		return ozone;
	}

	private void setTime(Integer time) {
		this.time = time;
	}

	private void setSummary(String summary) {
		this.summary = summary;
	}

	private void setIcon(FioIcon icon) {
		this.icon = icon;
	}

	private void setSunriseTime(Integer sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	private void setSunsetTime(Integer sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	private void setMoonPhase(BigDecimal moonPhase) {
		this.moonPhase = moonPhase;
	}

	private void setNearestStormDistance(BigDecimal nearestStormDistance) {
		this.nearestStormDistance = nearestStormDistance;
	}

	private void setNearestStormBearing(BigDecimal nearestStormBearing) {
		this.nearestStormBearing = nearestStormBearing;
	}

	private void setPrecipIntensity(BigDecimal precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	private void setPrecipIntensityMax(BigDecimal precipIntensityMax) {
		this.precipIntensityMax = precipIntensityMax;
	}

	private void setPrecipIntensityMaxTime(Integer precipIntensityMaxTime) {
		this.precipIntensityMaxTime = precipIntensityMaxTime;
	}

	private void setPrecipProbability(BigDecimal precipProbability) {
		this.precipProbability = precipProbability;
	}

	private void setPrecipType(FioPrecipType precipType) {
		this.precipType = precipType;
	}

	private void setPrecipAccumulation(BigDecimal precipAccumulation) {
		this.precipAccumulation = precipAccumulation;
	}

	private void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	private void setTemperatureMin(BigDecimal temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	private void setTemperatureMinTime(Integer temperatureMinTime) {
		this.temperatureMinTime = temperatureMinTime;
	}

	private void setTemperatureMax(BigDecimal temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	private void setTemperatureMaxTime(Integer temperatureMaxTime) {
		this.temperatureMaxTime = temperatureMaxTime;
	}

	private void setApparentTemperature(BigDecimal apparentTemperature) {
		this.apparentTemperature = apparentTemperature;
	}

	private void setApparentTemperatureMin(BigDecimal apparentTemperatureMin) {
		this.apparentTemperatureMin = apparentTemperatureMin;
	}

	private void setApparentTemperatureMinTime(Integer apparentTemperatureMinTime) {
		this.apparentTemperatureMinTime = apparentTemperatureMinTime;
	}

	private void setApparentTemperatureMax(BigDecimal apparentTemperatureMax) {
		this.apparentTemperatureMax = apparentTemperatureMax;
	}

	private void setApparentTemperatureMaxTime(Integer apparentTemperatureMaxTime) {
		this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
	}

	private void setDewPoint(BigDecimal dewPoint) {
		this.dewPoint = dewPoint;
	}

	private void setWindSpeed(BigDecimal windSpeed) {
		this.windSpeed = windSpeed;
	}

	private void setWindBearing(BigDecimal windBearing) {
		this.windBearing = windBearing;
	}

	private void setCloudCover(BigDecimal cloudCover) {
		this.cloudCover = cloudCover;
	}

	private void setHumidity(BigDecimal humidity) {
		this.humidity = humidity;
	}

	private void setPressure(BigDecimal pressure) {
		this.pressure = pressure;
	}

	private void setVisibility(BigDecimal visibility) {
		this.visibility = visibility;
	}

	private void setOzone(BigDecimal ozone) {
		this.ozone = ozone;
	}

	@Override
	public String toString() {
		return "FioDataPoint [time=" + time + ", summary=" + summary + ", icon=" + icon
				+ ", sunriseTime=" + sunriseTime + ", sunsetTime=" + sunsetTime
				+ ", moonPhase=" + moonPhase + ", nearestStormDistance="
				+ nearestStormDistance + ", nearestStormBearing=" + nearestStormBearing
				+ ", precipIntensity=" + precipIntensity + ", precipIntensityMax="
				+ precipIntensityMax + ", precipIntensityMaxTime="
				+ precipIntensityMaxTime + ", precipProbability=" + precipProbability
				+ ", precipType=" + precipType + ", precipAccumulation="
				+ precipAccumulation + ", temperature=" + temperature
				+ ", temperatureMin=" + temperatureMin + ", temperatureMinTime="
				+ temperatureMinTime + ", temperatureMax=" + temperatureMax
				+ ", temperatureMaxTime=" + temperatureMaxTime + ", apparentTemperature="
				+ apparentTemperature + ", apparentTemperatureMin="
				+ apparentTemperatureMin + ", apparentTemperatureMinTime="
				+ apparentTemperatureMinTime + ", apparentTemperatureMax="
				+ apparentTemperatureMax + ", apparentTemperatureMaxTime="
				+ apparentTemperatureMaxTime + ", dewPoint=" + dewPoint + ", windSpeed="
				+ windSpeed + ", windBearing=" + windBearing + ", cloudCover="
				+ cloudCover + ", humidity=" + humidity + ", pressure=" + pressure
				+ ", visibility=" + visibility + ", ozone=" + ozone + "]";
	}

}
