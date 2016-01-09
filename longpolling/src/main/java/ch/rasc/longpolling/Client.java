package ch.rasc.longpolling;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(3, TimeUnit.SECONDS);
		client.setWriteTimeout(10, TimeUnit.SECONDS);
		client.setReadTimeout(10, TimeUnit.SECONDS);		

		Request request = new Request.Builder().url("http://localhost:8080/event")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		System.out.println(response.body().string());

		System.out.println();
		System.out.println("THE END");

	}

}
