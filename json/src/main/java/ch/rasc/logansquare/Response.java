package ch.rasc.logansquare;

import java.util.List;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonObject
public class Response {

	@JsonField
	public List<User> users;

	@JsonField
	public String status;

	@JsonProperty("is_real_json") // Annotation needed for Jackson Databind
	@JsonField(name = "is_real_json")
	public boolean isRealJson;
}
