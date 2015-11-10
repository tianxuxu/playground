package ch.rasc.retrofit;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Main {

	public static void main(String[] args) throws IOException {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://httpbin.org")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();

		HttpBinService service = retrofit.create(HttpBinService.class);
		Call<Ip> ip = service.getIp();
		ip.enqueue(new Callback<Ip>() {
			
			@Override
			public void onResponse(Response<Ip> response, Retrofit retrofit) {
				System.out.println(response.body().getOrigin());
				
			}
			
			@Override
			public void onFailure(Throwable t) {
				// TODO Auto-generated method stub
				
			}
		});
		//Response<Ip> response = ip.execute();
		
		
		//System.out.println(response.body().getOrigin());
		
	}

}
