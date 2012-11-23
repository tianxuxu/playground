package ch.rasc.stats;

import java.util.List;

public class GeoCoderResults {
	private Status status;

	private List<GeoCoderResult> results;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<GeoCoderResult> getResults() {
		return results;
	}

	public void setResults(List<GeoCoderResult> results) {
		this.results = results;
	}

}
