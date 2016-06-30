package ch.rasc.forcastio;

import java.io.IOException;

import ch.rasc.forcastio.model.FioBlock;
import ch.rasc.forcastio.model.FioLanguage;
import ch.rasc.forcastio.model.FioRequest;
import ch.rasc.forcastio.model.FioResponse;
import ch.rasc.forcastio.model.FioUnit;
import ch.rasc.forcastio.model.ImmutableFioRequest;

public class Main {

	public static void main(String[] args) throws IOException {

		FioRequest request = ImmutableFioRequest.builder()
				.latitude("47.948915")
				.longitude("-100.1019949")
				.unit(FioUnit.SI)
				.language(FioLanguage.DE)
				.addIncludeBlock(FioBlock.DAILY)
				.build();

		FioClient client = new FioClient(args[0]);
		FioResponse response = client.forecastCall(request);
		System.out.println(response);
		System.out.println("number of api calls: " + client.apiCalls());
		System.out.println("response time: " + client.responseTime());


		request = ImmutableFioRequest.builder()
				.latitude("37.3861")
				.longitude("122.0839")
				.build();

		response = client.forecastCall(request);
		System.out.println(response);
		System.out.println("number of api calls: " + client.apiCalls());
		System.out.println("response time: " + client.responseTime());


		request = ImmutableFioRequest.builder()
				.latitude("42.7284")
				.longitude("73.6918")
				.extendHourly(true)
				.build();

		response = client.forecastCall(request);
		System.out.println(response);
		System.out.println("number of api calls: " + client.apiCalls());
		System.out.println("response time: " + client.responseTime());
		System.out.println(response.hourly().getAdditionalProperties());
		System.out.println(response.daily().getAdditionalProperties());

	}
}
