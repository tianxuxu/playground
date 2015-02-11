package ch.rasc.autovalue;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class User {
	static Builder builder() {
		return new AutoValue_User.Builder();
	}

	abstract long loginId();

	abstract String name();

	@AutoValue.Validate
	void validate() {
		if (loginId() < 0) {
			throw new IllegalStateException("Negative loginId");
		}
	}

	@AutoValue.Builder
	interface Builder {
		Builder loginId(long l);

		Builder name(String s);

		User build();
	}
}
