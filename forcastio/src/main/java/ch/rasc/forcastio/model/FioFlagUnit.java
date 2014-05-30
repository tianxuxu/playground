package ch.rasc.forcastio.model;

public enum FioFlagUnit {

	/**
	 * The default.
	 */
	US("us"),

	/**
	 * Returns results in SI units. In particular, properties now have the
	 * following units: summary: Any summaries containing temperature or snow
	 * accumulation units will have their values in degrees Celsius or in
	 * centimeters (respectively). precipIntensity: Millimeters per hour.
	 * precipAccumulation: Centimeters. temperature: Degrees Celsius. dewPoint:
	 * Degrees Celsius. windSpeed: Meters per second. pressure: Hectopascals
	 * (which are, conveniently, equivalent to the default millibars).
	 * visibility: Kilometers.
	 */
	SI("si"),

	/**
	 * Identical to si, except that windSpeed is in kilometers per hour.
	 */
	CA("ca"),

	/**
	 * Identical to si, except that windSpeed is in miles per hour, as in the
	 * US.
	 */
	UK("uk"),

	UNKNOWN(null);

	private String jsonValue;

	private FioFlagUnit(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static FioFlagUnit findByJsonValue(String jsonValue) {
		for (FioFlagUnit en : FioFlagUnit.values()) {
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
