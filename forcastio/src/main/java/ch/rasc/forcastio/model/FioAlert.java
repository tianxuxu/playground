package ch.rasc.forcastio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An alert object represents a severe weather warning issued for the requested location
 * by a governmental authority
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FioAlert {

	private String title;
	private Integer expires;
	private String uri;
	private String description;

	/**
	 * A short text summary of the alert.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which the
	 * alert will cease to be valid.
	 */
	public Integer getExpires() {
		return this.expires;
	}

	/**
	 * An HTTP(S) URI that contains detailed information about the alert.
	 */
	public String getUri() {
		return this.uri;
	}

	/**
	 * A detailed text description of the alert from the appropriate weather service.
	 */
	public String getDescription() {
		return this.description;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private void setExpires(Integer expires) {
		this.expires = expires;
	}

	private void setUri(String uri) {
		this.uri = uri;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "FioAlert [title=" + this.title + ", expires=" + this.expires + ", uri="
				+ this.uri + ", description=" + this.description + "]";
	}

}
