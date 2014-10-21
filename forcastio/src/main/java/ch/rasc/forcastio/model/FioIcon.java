package ch.rasc.forcastio.model;

public enum FioIcon {
	CLEAR_DAY("clear-day"), CLEAR_NIGHT("clear-night"), RAIN("rain"), SNOW("snow"), SLEET(
			"sleet"), WIND("wind"), FOG("fog"), CLOUDY("cloudy"), PARTLY_CLOUDY_DAY(
			"partly-cloudy-day"), PARTLY_CLOUDY_NIGHT("partly-cloudy-night"), UNKNOWN(
			null);

	private String jsonValue;

	private FioIcon(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static FioIcon findByJsonValue(String jsonValue) {
		for (FioIcon en : FioIcon.values()) {
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
