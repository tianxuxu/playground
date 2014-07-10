package ch.rasc.forcastio.model;

import java.util.List;

/**
 * A data block object represents the various weather phenomena occurring over a period of
 * time. Ideally, the minutely data block will contain data for the next hour, the hourly
 * data block for the next two days, and the daily data block for the next week; however,
 * if we are lacking data for a given time period, the data point sequence may contain
 * gaps or terminate early. Furthermore, if no data points for a time period are known,
 * then the data block will be omitted from the response in its entirety.
 */
public class FioDataBlock {

	/**
	 * A human-readable text summary of this data block.
	 */
	private String summary;

	/**
	 * A machine-readable text summary of this data block (see data point, above, for an
	 * enumeration of possible values that this property may take on).
	 */
	private String icon;

	/**
	 * An array of data point objects, ordered by time, which together describe the
	 * weather conditions at the requested location over time.
	 */
	private List<FioDataPoint> data;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<FioDataPoint> getData() {
		return data;
	}

	public void setData(List<FioDataPoint> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FioDataBlock [summary=" + summary + ", icon=" + icon + ", data=" + data
				+ "]";
	}

}
