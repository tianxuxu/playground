package ch.rasc.forcastio.model;

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.forcastio.converter.FioUnitDeserializer;

/**
 * The flags object contains various metadata information related to the request.
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableFioFlag.class)
public interface FioFlag {

	/**
	 * The presence of this property indicates that the Dark Sky data source supports the
	 * given location, but a temporary error (such as a radar station being down for
	 * maintenace) has made the data unavailable.
	 */
	@Nullable
	@JsonProperty("darksky-unavailable")
	Object darkskyUnavailable();

	/**
	 * This property contains an array of IDs for each radar station utilized in servicing
	 * this request.
	 */
	@JsonProperty("darksky-stations")
	List<String> darkskyStations();

	/**
	 * This property contains an array of IDs for each DataPoint station utilized in
	 * servicing this request.
	 */
	@JsonProperty("datapoint-stations")
	List<String> datapointStations();

	/**
	 * This property contains an array of IDs for each ISD station utilized in servicing
	 * this request.
	 */
	@JsonProperty("isd-stations")
	List<String> isdStations();

	/**
	 * This property contains an array of IDs for each LAMP station utilized in servicing
	 * this request.
	 */
	@JsonProperty("lamp-stations")
	List<String> lampStations();

	/**
	 * This property contains an array of IDs for each METAR station utilized in servicing
	 * this request.
	 */
	@JsonProperty("metar-stations")
	List<String> metarStations();

	/**
	 * The presence of this property indicates that data from api.met.no was utilized in
	 * order to facilitate this request (as per their license agreement).
	 */
	@Nullable
	@JsonProperty("metno-license")
	String metnoLicense();

	/**
	 * This property contains an array of IDs for each data source utilized in servicing
	 * this request.
	 */
	List<String> sources();

	/**
	 * The presence of this property indicates which units were used for the data in this
	 * request.
	 */
	@Nullable
	@JsonDeserialize(using = FioUnitDeserializer.class)
	FioUnit units();

}
