package ch.rasc.forcastio.model;

public enum FioUnit {

	/**
	 * The default.
	 */
	US("us"),

	/**
	 * Returns results in SI units. In particular, properties now have the following
	 * units:
	 * <p>
	 * <ul>
	 * <li>summary: Any summaries containing temperature or snow accumulation units will
	 * have their values in degrees Celsius or in centimeters (respectively).</li>
	 * <li>nearestStormDistance: Kilometers.</li>
	 * <li>precipIntensity: Millimeters per hour.</li>
	 * <li>precipIntensityMax: Millimeters per hour.</li>
	 * <li>precipAccumulation: Centimeters.</li>
	 * <li>temperature: Degrees Celsius.</li>
	 * <li>temperatureMin: Degrees Celsius.</li>
	 * <li>temperatureMax: Degrees Celsius.</li>
	 * <li>apparentTemperature: Degrees Celsius.</li>
	 * <li>dewPoint: Degrees Celsius.</li>
	 * <li>windSpeed: Meters per second.</li>
	 * <li>pressure: Hectopascals (which are, conveniently, equivalent to the default
	 * millibars).</li>
	 * <li>visibility: Kilometers.</li>
	 * </ul>
	 */
	SI("si"),

	/**
	 * Identical to si, except that windSpeed is in kilometers per hour.
	 */
	CA("ca"),

	/**
	 * Identical to si, except that windSpeed is in miles per hour, and
	 * nearestStormDistance and visibility are in miles, as in the US. (This option is
	 * provided because adoption of SI in the UK has been inconsistent.)
	 */
	UK2("uk2"),

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
		return this.jsonValue;
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
