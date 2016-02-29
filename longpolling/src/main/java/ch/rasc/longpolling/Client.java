package ch.rasc.longpolling;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(3, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS).build();

		Request request = new Request.Builder().url("http://localhost:8080/event")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		System.out.println(response.body().string());

		System.out.println();
		System.out.println("THE END");

	}

}
