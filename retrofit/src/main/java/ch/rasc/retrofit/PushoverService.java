package ch.rasc.retrofit;

import java.util.Map;

import retrofit.Call;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface PushoverService {

	@POST("/1/messages.json")
	@FormUrlEncoded
	Call<PushoverResponse> sendMessage(@FieldMap Map<String, Object> fields);

}
