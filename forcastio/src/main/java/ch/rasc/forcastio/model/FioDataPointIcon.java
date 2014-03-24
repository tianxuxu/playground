package ch.rasc.forcastio.model;

public enum FioDataPointIcon {
	CLEAR_DAY("clear-day"), CLEAR_NIGHT("clear-night"), RAIN("rain"), SNOW("snow"), SLEET("sleet"), WIND("wind"), FOG(
			"fog"), CLOUDY("cloudy"), PARTLY_CLOUDY_DAY("partly-cloudy-day"), PARTLY_CLOUDY_NIGHT("partly-cloudy-night"), UNKNOWN(
					null);

	private String jsonValue;

	private FioDataPointIcon(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static FioDataPointIcon findByJsonValue(String jsonValue) {
		for (FioDataPointIcon en : FioDataPointIcon.values()) {
			if (jsonValue.equals(en.jsonValue)) {
				return en;
			}
		}

		if (jsonValue != null) {
			return UNKNOWN;
		}

		return null;
	}
}
