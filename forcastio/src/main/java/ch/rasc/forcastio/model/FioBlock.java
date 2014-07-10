package ch.rasc.forcastio.model;

public enum FioBlock {

	CURRENTLY("currently"), MINUTELY("minutely"), HOURLY("hourly"), DAILY("daily"), ALERTS(
			"alerts"), FLAGS("flags");

	private String jsonValue;

	private FioBlock(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getJsonValue() {
		return jsonValue;
	}

}
