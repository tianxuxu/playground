package ch.rasc.forcastio.model;

import java.util.Date;

/**
 * An alert object represents a severe weather warning issued for the requested
 * location by a governmental authority.
 */
public class FioAlert {

	/**
	 * A short text summary of the alert.
	 */
	private String title;

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at
	 * which the alert will cease to be valid.
	 */
	private Date expires;

	/**
	 * An HTTP(S) URI that contains detailed information about the alert.
	 */
	private String uri;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "FioAlert [title=" + title + ", expires=" + expires + ", uri=" + uri + "]";
	}

}
