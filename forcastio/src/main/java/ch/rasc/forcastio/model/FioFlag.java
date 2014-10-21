package ch.rasc.forcastio.model;

import java.util.List;

import ch.rasc.forcastio.converter.FioUnitDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The flags object contains various metadata information related to the request.
 */
@SuppressWarnings("unused")
public class FioFlag {

	@JsonProperty("darksky-unavailable")
	private Object darkskyUnavailable;

	@JsonProperty("darksky-stations")
	private List<String> darkskyStations;

	@JsonProperty("datapoint-stations")
	private List<String> datapointStations;

	@JsonProperty("isd-stations")
	private List<String> isdStations;

	@JsonProperty("lamp-stations")
	private List<String> lampStations;

	@JsonProperty("metar-stations")
	private List<String> metarStations;

	@JsonProperty("madis-stations")
	private List<String> madisStations;

	@JsonProperty("metno-license")
	private String metnoLicense;

	private List<String> sources;

	@JsonDeserialize(using = FioUnitDeserializer.class)
	private FioUnit units;

	/**
	 * The presence of this property indicates that the Dark Sky data source supports the
	 * given location, but a temporary error (such as a radar station being down for
	 * maintenace) has made the data unavailable.
	 */
	public Object getDarkskyUnavailable() {
		return darkskyUnavailable;
	}

	/**
	 * This property contains an array of IDs for each radar station utilized in servicing
	 * this request.
	 */
	public List<String> getDarkskyStations() {
		return darkskyStations;
	}

	/**
	 * This property contains an array of IDs for each DataPoint station utilized in
	 * servicing this request.
	 */
	public List<String> getDatapointStations() {
		return datapointStations;
	}

	/**
	 * This property contains an array of IDs for each ISD station utilized in servicing
	 * this request.
	 */
	public List<String> getIsdStations() {
		return isdStations;
	}

	/**
	 * This property contains an array of IDs for each LAMP station utilized in servicing
	 * this request.
	 */
	public List<String> getLampStations() {
		return lampStations;
	}

	/**
	 * This property contains an array of IDs for each METAR station utilized in servicing
	 * this request.
	 */
	public List<String> getMetarStations() {
		return metarStations;
	}

	public List<String> getMadisStations() {
		return madisStations;
	}

	/**
	 * The presence of this property indicates that data from api.met.no was utilized in
	 * order to facilitate this request (as per their license agreement).
	 */
	public String getMetnoLicense() {
		return metnoLicense;
	}

	/**
	 * This property contains an array of IDs for each data source utilized in servicing
	 * this request.
	 */
	public List<String> getSources() {
		return sources;
	}

	/**
	 * The presence of this property indicates which units were used for the data in this
	 * request.
	 */
	public FioUnit getUnits() {
		return units;
	}

	private void setDarkskyUnavailable(Object darkskyUnavailable) {
		this.darkskyUnavailable = darkskyUnavailable;
	}

	private void setDarkskyStations(List<String> darkskyStations) {
		this.darkskyStations = darkskyStations;
	}

	private void setDatapointStations(List<String> datapointStations) {
		this.datapointStations = datapointStations;
	}

	private void setIsdStations(List<String> isdStations) {
		this.isdStations = isdStations;
	}

	private void setLampStations(List<String> lampStations) {
		this.lampStations = lampStations;
	}

	private void setMetarStations(List<String> metarStations) {
		this.metarStations = metarStations;
	}

	private void setMadisStations(List<String> madisStations) {
		this.madisStations = madisStations;
	}

	private void setMetnoLicense(String metnoLicense) {
		this.metnoLicense = metnoLicense;
	}

	private void setSources(List<String> sources) {
		this.sources = sources;
	}

	private void setUnits(FioUnit units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "FioFlag [darkskyUnavailable=" + darkskyUnavailable + ", darkskyStations="
				+ darkskyStations + ", datapointStations=" + datapointStations
				+ ", isdStations=" + isdStations + ", lampStations=" + lampStations
				+ ", metarStations=" + metarStations + ", madisStations=" + madisStations
				+ ", metnoLicense=" + metnoLicense + ", sources=" + sources + ", units="
				+ units + "]";
	}

}
