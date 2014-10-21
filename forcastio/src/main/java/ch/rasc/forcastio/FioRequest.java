package ch.rasc.forcastio;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import ch.rasc.forcastio.model.FioBlock;
import ch.rasc.forcastio.model.FioResponse;
import ch.rasc.forcastio.model.FioUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FioRequest {

	private final static String FORECAST_IO_URL = "https://api.forecast.io/forecast/";

	private final String apiKey;

	private final String latitude;

	private final String longitude;

	private boolean extend = false;

	private String language = null;

	private String units = null;

	private EnumSet<FioBlock> excludeBlocks = null;

	private EnumSet<FioBlock> includeBlocks = null;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private FioRequest(String apiKey, String latitude, String longitude) {
		this.apiKey = apiKey;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static FioRequest create(String apiKey, String latitude, String longitude) {
		return new FioRequest(apiKey, latitude, longitude);
	}

	public static FioRequest create(String apiKey, BigDecimal latitude,
			BigDecimal longitude) {
		return new FioRequest(apiKey, latitude.toString(), longitude.toString());
	}

	public static FioRequest create(String apiKey, double latitude, double longitude) {
		return new FioRequest(apiKey, String.valueOf(latitude), String.valueOf(longitude));
	}

	/**
	 * Return the API response in units other than the default Imperial units
	 */
	public FioRequest unit(FioUnit fioUnit) {
		if (fioUnit != FioUnit.US) {
			units = fioUnit.getJsonValue();
		}
		return this;
	}

	/**
	 * When present on a forecast request, return hourly data for the next seven days,
	 * rather than the next two. (This option is ignored on time machine requests).
	 */
	public FioRequest extendHourly() {
		this.extend = true;
		return this;
	}

	/**
	 * Exclude some number of data blocks from the API response. This is useful for
	 * reducing latency and saving cache space.
	 */
	public FioRequest exclude(FioBlock... blocks) {
		if (blocks != null && blocks.length > 0) {
			if (excludeBlocks == null) {
				excludeBlocks = EnumSet.noneOf(FioBlock.class);
			}

			for (FioBlock fioBlock : blocks) {
				excludeBlocks.add(fioBlock);
			}
		}

		return this;
	}

	/**
	 * Include some number of data blocks in the API response. Every block that is not
	 * specifed is automatically excluded.
	 */
	public FioRequest include(FioBlock... blocks) {
		if (blocks != null && blocks.length > 0) {
			if (includeBlocks == null) {
				includeBlocks = EnumSet.noneOf(FioBlock.class);
			}

			for (FioBlock fioBlock : blocks) {
				includeBlocks.add(fioBlock);
			}
		}

		return this;
	}

	/**
	 * Return text summaries in the desired language.
	 */
	public FioRequest language(FioLanguage fioLanguage) {
		if (fioLanguage != FioLanguage.EN) {
			language = fioLanguage.name().toLowerCase().replace('_', '-');
		}

		return this;
	}

	public FioResponse execute() throws ClientProtocolException, IOException {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

			StringBuilder url = new StringBuilder(FORECAST_IO_URL);
			url.append(apiKey);
			url.append("/");
			url.append(latitude);
			url.append(",");
			url.append(longitude);

			Map<String, String> queryParameters = new HashMap<>();

			if (units != null) {
				queryParameters.put("units", units);
			}

			if (extend) {
				queryParameters.put("extend", "hourly");
			}

			if (language != null) {
				queryParameters.put("lang", language);
			}

			if (includeBlocks != null && !includeBlocks.isEmpty()) {
				EnumSet<FioBlock> exclude = EnumSet.complementOf(includeBlocks);
				if (excludeBlocks == null) {
					excludeBlocks = exclude;
				}
				else {
					excludeBlocks.addAll(exclude);
				}
			}

			if (excludeBlocks != null && !excludeBlocks.isEmpty()) {
				if (excludeBlocks.size() == FioBlock.values().length) {
					// Everything excluded
					return null;
				}
				queryParameters.put(
						"exclude",
						excludeBlocks.stream().map(FioBlock::getJsonValue)
								.collect(Collectors.joining(",")));
			}

			if (!queryParameters.isEmpty()) {
				url.append("?").append(
						queryParameters.entrySet().stream()
								.map((e) -> e.getKey() + "=" + e.getValue())
								.collect(Collectors.joining("&")));
			}

			System.out.println(url);

			HttpGet httpget = new HttpGet(url.toString());
			try (CloseableHttpResponse response = httpClient.execute(httpget)) {
				HttpEntity entity = response.getEntity();
				String jsonData = EntityUtils.toString(entity, StandardCharsets.UTF_8);
				return objectMapper.readValue(jsonData, FioResponse.class);
			}

		}
	}

}
