package ch.rasc.springmongodb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class City {

	@Id
	private String id;

	private String country;

	private String asciiCityName;

	@Indexed
	private String cityName;

	private String region;

	private Integer population;

	@GeoSpatialIndexed
	private Point location;

	public String getCountry() {
		return country;
	}

	public String getAsciiCityName() {
		return asciiCityName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getRegion() {
		return region;
	}

	public Integer getPopulation() {
		return population;
	}

	public Point getLocation() {
		return location;
	}

	public String getId() {
		return id;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAsciiCityName(String asciiCityName) {
		this.asciiCityName = asciiCityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

}
