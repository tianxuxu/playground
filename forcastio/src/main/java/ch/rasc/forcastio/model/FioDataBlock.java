package ch.rasc.forcastio.model;

import java.util.List;

import ch.rasc.forcastio.converter.FioIconDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * A data block object represents the various weather phenomena occurring over a period of
 * time.
 *
 * Ideally, the minutely data block will contain data for the next hour, the hourly data
 * block for the next two days, and the daily data block for the next week; however, if we
 * are lacking data for a given time period, the data point sequence may contain gaps or
 * terminate early. Furthermore, if no data points for a time period are known, then the
 * data block will be omitted from the response in its entirety.
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FioDataBlock {
	private String summary;

	@JsonDeserialize(using = FioIconDeserializer.class)
	private FioIcon icon;

	private List<FioDataPoint> data;

	/**
	 * A human-readable text summary of this data block.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * A machine-readable text summary of this data block
	 */
	public FioIcon getIcon() {
		return icon;
	}

	/**
	 * An array of data point objects, ordered by time, which together describe the
	 * weather conditions at the requested location over time.
	 */
	public List<FioDataPoint> getData() {
		return data;
	}

	private void setSummary(String summary) {
		this.summary = summary;
	}

	private void setIcon(FioIcon icon) {
		this.icon = icon;
	}

	private void setData(List<FioDataPoint> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FioDataBlock [summary=" + summary + ", icon=" + icon + ", data=" + data
				+ "]";
	}

}
