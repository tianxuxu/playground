package ch.rasc.forcastio.model;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * An alert object represents a severe weather warning issued for the requested location
 * by a governmental authority
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableFioAlert.class)
public interface FioAlert {

	/**
	 * A short text summary of the alert.
	 */
	String title();

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which the
	 * alert will cease to be valid.
	 */
	long expires();

	/**
	 * An HTTP(S) URI that contains detailed information about the alert.
	 */
	String uri();

	/**
	 * A detailed text description of the alert from the appropriate weather service.
	 */
	String description();

}
