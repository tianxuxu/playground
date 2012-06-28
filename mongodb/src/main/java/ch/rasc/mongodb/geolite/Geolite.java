package ch.rasc.mongodb.geolite;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;

@Entity(noClassnameStored = true)
// @Indexes({ @Index("startIpNum,endIpNum") })
public class Geolite {

	@Id
	private ObjectId id;

	@Indexed(unique = true, value = IndexDirection.DESC)
	private long startIpNum;

	private long endIpNum;

	private String country;

	private String region;

	private String city;

	private String postalCode;

	private double latitude;

	private double longitude;

	private String metroCode;

	private String areaCode;

	public long getStartIpNum() {
		return startIpNum;
	}

	public void setStartIpNum(final long startIpNum) {
		this.startIpNum = startIpNum;
	}

	public long getEndIpNum() {
		return endIpNum;
	}

	public void setEndIpNum(final long endIpNum) {
		this.endIpNum = endIpNum;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	public String getMetroCode() {
		return metroCode;
	}

	public void setMetroCode(final String metroCode) {
		this.metroCode = metroCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(final String areaCode) {
		this.areaCode = areaCode;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(final ObjectId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Geolite [id=" + id + ", startIpNum=" + startIpNum + ", endIpNum=" + endIpNum + ", country=" + country
				+ ", region=" + region + ", city=" + city + ", postalCode=" + postalCode + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", metroCode=" + metroCode + ", areaCode=" + areaCode + "]";
	}

}
