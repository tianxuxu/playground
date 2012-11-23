package ch.rasc.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {

	private Area bounds;

	@JsonProperty("location_type")
	private LocationType locationType;

	private LatLng location;

	private Area viewport;

	public Area getBounds() {
		return bounds;
	}

	public void setBounds(Area bounds) {
		this.bounds = bounds;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public Area getViewport() {
		return viewport;
	}

	public void setViewport(Area viewport) {
		this.viewport = viewport;
	}

}
