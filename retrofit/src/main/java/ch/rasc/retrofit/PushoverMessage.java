package ch.rasc.retrofit;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
abstract interface PushoverMessage {

	String token();

	String user();

	String message();

	@Nullable
	String device();

	@Nullable
	String title();

	@Nullable
	String url();

	@Nullable
	String url_title();

	@Nullable
	Integer priority();

	@Nullable
	Long timestamp();

	@Nullable
	String sound();

}