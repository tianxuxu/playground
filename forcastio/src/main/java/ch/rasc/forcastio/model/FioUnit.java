package ch.rasc.forcastio.model;

public enum FioUnit {

	/**
	 * The default.
	 */
	US("us"),

	/**
	 * Returns results in SI units. In particular, properties now have the following
	 * units: summary: Any summaries containing temperature or snow accumulation units
	 * will have their values in degrees Celsius or in centimeters (respectively).
	 * nearestStormDistance: Kilometers. precipIntensity: Millimeters per hour.
	 * precipIntensityMax: Millimeters per hour. precipAccumulation: Centimeters.
	 * temperature: Degrees Celsius. temperatureMin: Degrees Celsius. temperatureMax:
	 * Degrees Celsius. apparentTemperature: Degrees Celsius. dewPoint: Degrees Celsius.
	 * windSpeed: Meters per second. pressure: Hectopascals (which are, conveniently,
	 * equivalent to the default millibars). visibility: Kilometers.
	 */
	SI("si"),

	/**
	 * Identical to si, except that windSpeed is in kilometers per hour.
	 */
	CA("ca"),

	/**
	 * Identical to si, except that windSpeed is in miles per hour, as in the US. (This
	 * option is provided because adoption of SI in the UK has been inconsistent.)
	 */
	UK("uk"),

	/**
	 * Selects the relevant units automatically, based on geographic location.
	 */
	AUTO("auto"),

	UNKNOWN(null);

	private String jsonValue;

	private FioUnit(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public static FioUnit findByJsonValue(String jsonValue) {
		for (FioUnit unit : FioUnit.values()) {
			if (jsonValue.equals(unit.jsonValue)) {
				return unit;
			}
		}

		if (jsonValue != null) {
			return UNKNOWN;
		}

		return null;
	}
}
