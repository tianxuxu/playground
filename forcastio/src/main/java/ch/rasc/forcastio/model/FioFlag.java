package ch.rasc.forcastio.model;

import java.util.List;

import ch.rasc.forcastio.converter.FioFlagUnitDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The flags object contains various metadata information related to the request.
 */
public class FioFlag {

	/**
	 * The presence of this property indicates that the Dark Sky data source supports the
	 * given location, but a temporary error (such as a radar station being down for
	 * maintenace) has made the data unavailable.
	 */
	@JsonProperty("darksky-unavailable")
	private Object darkskyUnavailable;

	/**
	 * This property contains an array of IDs for each radar station utilized in servicing
	 * this request.
	 */
	@JsonProperty("darksky-stations")
	private List<String> darkskyStations;

	/**
	 * This property contains an array of IDs for each DataPoint station utilized in
	 * servicing this request.
	 */
	@JsonProperty("datapoint-stations")
	private List<String> datapointStations;

	/**
	 * This property contains an array of IDs for each ISD station utilized in servicing
	 * this request.
	 */
	@JsonProperty("isd-stations")
	private List<String> isdStations;

	/**
	 * This property contains an array of IDs for each LAMP station utilized in servicing
	 * this request.
	 */
	@JsonProperty("lamp-stations")
	private List<String> lampStations;

	/**
	 * This property contains an array of IDs for each METAR station utilized in servicing
	 * this request.
	 */
	@JsonProperty("metar-stations")
	private List<String> metarStations;

	/**
	 * The presence of this property indicates that data from api.met.no was utilized in
	 * order to facilitate this request (as per their license agreement).
	 */
	@JsonProperty("metno-license")
	private List<String> metnoLicense;

	/**
	 * This property contains an array of IDs for each data source utilized in servicing
	 * this request. (For more information, see data sources, below.)
	 */
	private List<String> sources;

	/**
	 * The presence of this property indicates which units were used for the data in this
	 * request. (For more information, see options, below.)
	 */
	@JsonDeserialize(using = FioFlagUnitDeserializer.class)
	private FioFlagUnit units;

	public Object getDarkskyUnavailable() {
		return darkskyUnavailable;
	}

	public void setDarkskyUnavailable(Object darkskyUnavailable) {
		this.darkskyUnavailable = darkskyUnavailable;
	}

	public List<String> getDarkskyStations() {
		return darkskyStations;
	}

	public void setDarkskyStations(List<String> darkskyStations) {
		this.darkskyStations = darkskyStations;
	}

	public List<String> getDatapointStations() {
		return datapointStations;
	}

	public void setDatapointStations(List<String> datapointStations) {
		this.datapointStations = datapointStations;
	}

	public List<String> getIsdStations() {
		return isdStations;
	}

	public void setIsdStations(List<String> isdStations) {
		this.isdStations = isdStations;
	}

	public List<String> getLampStations() {
		return lampStations;
	}

	public void setLampStations(List<String> lampStations) {
		this.lampStations = lampStations;
	}

	public List<String> getMetarStations() {
		return metarStations;
	}

	public void setMetarStations(List<String> metarStations) {
		this.metarStations = metarStations;
	}

	public List<String> getMetnoLicense() {
		return metnoLicense;
	}

	public void setMetnoLicense(List<String> metnoLicense) {
		this.metnoLicense = metnoLicense;
	}

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	public FioFlagUnit getUnits() {
		return units;
	}

	public void setUnits(FioFlagUnit units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return "FioFlag [darkskyUnavailable=" + darkskyUnavailable + ", darkskyStations="
				+ darkskyStations + ", datapointStations=" + datapointStations
				+ ", isdStations=" + isdStations + ", lampStations=" + lampStations
				+ ", metarStations=" + metarStations + ", metnoLicense=" + metnoLicense
				+ ", sources=" + sources + ", units=" + units + "]";
	}

}
