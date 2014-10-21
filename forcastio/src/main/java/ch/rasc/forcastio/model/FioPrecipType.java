package ch.rasc.forcastio.model;

public enum FioPrecipType {
	RAIN("rain"), SNOW("snow"), SLEET("sleet"), HAIL("hail"), UNKNOWN(null);

	private String jsonValue;

	private FioPrecipType(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static FioPrecipType findByJsonValue(String jsonValue) {
		for (FioPrecipType en : FioPrecipType.values()) {
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
