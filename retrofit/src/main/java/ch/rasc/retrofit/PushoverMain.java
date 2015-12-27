package ch.rasc.retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PushoverMain {
	public static void main(String... args) throws IOException {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.pushover.net")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();

		PushoverService service = retrofit.create(PushoverService.class);
		
		PushoverMessage msg = ImmutablePushoverMessage.builder().message("test message").token(args[0]).user(args[1]).build();
		
		Map<String,Object> obj = new HashMap<>();
		obj.put("token", msg.token());
		obj.put("user", msg.user());
		obj.put("message", msg.message());
		Call<PushoverResponse> response = service.sendMessage(obj);
		
		Response<PushoverResponse> resp = response.execute();
		System.out.println(resp.body());
	}
}
