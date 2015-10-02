/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package ch.rasc.avro;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class User extends org.apache.avro.specific.SpecificRecordBase
		implements org.apache.avro.specific.SpecificRecord {
	public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser()
			.parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"ch.rasc.avro\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"favorite_number\",\"type\":[\"int\",\"null\"]},{\"name\":\"favorite_color\",\"type\":[\"string\",\"null\"]}]}");

	public static org.apache.avro.Schema getClassSchema() {
		return SCHEMA$;
	}

	@Deprecated
	public java.lang.CharSequence name;
	@Deprecated
	public java.lang.Integer favorite_number;
	@Deprecated
	public java.lang.CharSequence favorite_color;

	/**
	 * Default constructor. Note that this does not initialize fields to their default
	 * values from the schema. If that is desired then one should use
	 * <code>newBuilder()</code>.
	 */
	public User() {
	}

	/**
	 * All-args constructor.
	 */
	public User(java.lang.CharSequence name, java.lang.Integer favorite_number,
			java.lang.CharSequence favorite_color) {
		this.name = name;
		this.favorite_number = favorite_number;
		this.favorite_color = favorite_color;
	}

	@Override
	public org.apache.avro.Schema getSchema() {
		return SCHEMA$;
	}

	// Used by DatumWriter. Applications should not call.
	@Override
	public java.lang.Object get(int field$) {
		switch (field$) {
		case 0:
			return this.name;
		case 1:
			return this.favorite_number;
		case 2:
			return this.favorite_color;
		default:
			throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
	}

	// Used by DatumReader. Applications should not call.
	@Override
	@SuppressWarnings(value = "unchecked")
	public void put(int field$, java.lang.Object value$) {
		switch (field$) {
		case 0:
			this.name = (java.lang.CharSequence) value$;
			break;
		case 1:
			this.favorite_number = (java.lang.Integer) value$;
			break;
		case 2:
			this.favorite_color = (java.lang.CharSequence) value$;
			break;
		default:
			throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
	}

	/**
	 * Gets the value of the 'name' field.
	 */
	public java.lang.CharSequence getName() {
		return this.name;
	}

	/**
	 * Sets the value of the 'name' field.
	 * @param value the value to set.
	 */
	public void setName(java.lang.CharSequence value) {
		this.name = value;
	}

	/**
	 * Gets the value of the 'favorite_number' field.
	 */
	public java.lang.Integer getFavoriteNumber() {
		return this.favorite_number;
	}

	/**
	 * Sets the value of the 'favorite_number' field.
	 * @param value the value to set.
	 */
	public void setFavoriteNumber(java.lang.Integer value) {
		this.favorite_number = value;
	}

	/**
	 * Gets the value of the 'favorite_color' field.
	 */
	public java.lang.CharSequence getFavoriteColor() {
		return this.favorite_color;
	}

	/**
	 * Sets the value of the 'favorite_color' field.
	 * @param value the value to set.
	 */
	public void setFavoriteColor(java.lang.CharSequence value) {
		this.favorite_color = value;
	}

	/** Creates a new User RecordBuilder */
	public static ch.rasc.avro.User.Builder newBuilder() {
		return new ch.rasc.avro.User.Builder();
	}

	/** Creates a new User RecordBuilder by copying an existing Builder */
	public static ch.rasc.avro.User.Builder newBuilder(ch.rasc.avro.User.Builder other) {
		return new ch.rasc.avro.User.Builder(other);
	}

	/** Creates a new User RecordBuilder by copying an existing User instance */
	public static ch.rasc.avro.User.Builder newBuilder(ch.rasc.avro.User other) {
		return new ch.rasc.avro.User.Builder(other);
	}

	/**
	 * RecordBuilder for User instances.
	 */
	public static class Builder
			extends org.apache.avro.specific.SpecificRecordBuilderBase<User>
			implements org.apache.avro.data.RecordBuilder<User> {

		private java.lang.CharSequence name;
		private java.lang.Integer favorite_number;
		private java.lang.CharSequence favorite_color;

		/** Creates a new Builder */
		private Builder() {
			super(ch.rasc.avro.User.SCHEMA$);
		}

		/** Creates a Builder by copying an existing Builder */
		private Builder(ch.rasc.avro.User.Builder other) {
			super(other);
			if (isValidValue(fields()[0], other.name)) {
				this.name = data().deepCopy(fields()[0].schema(), other.name);
				fieldSetFlags()[0] = true;
			}
			if (isValidValue(fields()[1], other.favorite_number)) {
				this.favorite_number = data().deepCopy(fields()[1].schema(),
						other.favorite_number);
				fieldSetFlags()[1] = true;
			}
			if (isValidValue(fields()[2], other.favorite_color)) {
				this.favorite_color = data().deepCopy(fields()[2].schema(),
						other.favorite_color);
				fieldSetFlags()[2] = true;
			}
		}

		/** Creates a Builder by copying an existing User instance */
		private Builder(ch.rasc.avro.User other) {
			super(ch.rasc.avro.User.SCHEMA$);
			if (isValidValue(fields()[0], other.name)) {
				this.name = data().deepCopy(fields()[0].schema(), other.name);
				fieldSetFlags()[0] = true;
			}
			if (isValidValue(fields()[1], other.favorite_number)) {
				this.favorite_number = data().deepCopy(fields()[1].schema(),
						other.favorite_number);
				fieldSetFlags()[1] = true;
			}
			if (isValidValue(fields()[2], other.favorite_color)) {
				this.favorite_color = data().deepCopy(fields()[2].schema(),
						other.favorite_color);
				fieldSetFlags()[2] = true;
			}
		}

		/** Gets the value of the 'name' field */
		public java.lang.CharSequence getName() {
			return this.name;
		}

		/** Sets the value of the 'name' field */
		public ch.rasc.avro.User.Builder setName(java.lang.CharSequence value) {
			validate(fields()[0], value);
			this.name = value;
			fieldSetFlags()[0] = true;
			return this;
		}

		/** Checks whether the 'name' field has been set */
		public boolean hasName() {
			return fieldSetFlags()[0];
		}

		/** Clears the value of the 'name' field */
		public ch.rasc.avro.User.Builder clearName() {
			this.name = null;
			fieldSetFlags()[0] = false;
			return this;
		}

		/** Gets the value of the 'favorite_number' field */
		public java.lang.Integer getFavoriteNumber() {
			return this.favorite_number;
		}

		/** Sets the value of the 'favorite_number' field */
		public ch.rasc.avro.User.Builder setFavoriteNumber(java.lang.Integer value) {
			validate(fields()[1], value);
			this.favorite_number = value;
			fieldSetFlags()[1] = true;
			return this;
		}

		/** Checks whether the 'favorite_number' field has been set */
		public boolean hasFavoriteNumber() {
			return fieldSetFlags()[1];
		}

		/** Clears the value of the 'favorite_number' field */
		public ch.rasc.avro.User.Builder clearFavoriteNumber() {
			this.favorite_number = null;
			fieldSetFlags()[1] = false;
			return this;
		}

		/** Gets the value of the 'favorite_color' field */
		public java.lang.CharSequence getFavoriteColor() {
			return this.favorite_color;
		}

		/** Sets the value of the 'favorite_color' field */
		public ch.rasc.avro.User.Builder setFavoriteColor(java.lang.CharSequence value) {
			validate(fields()[2], value);
			this.favorite_color = value;
			fieldSetFlags()[2] = true;
			return this;
		}

		/** Checks whether the 'favorite_color' field has been set */
		public boolean hasFavoriteColor() {
			return fieldSetFlags()[2];
		}

		/** Clears the value of the 'favorite_color' field */
		public ch.rasc.avro.User.Builder clearFavoriteColor() {
			this.favorite_color = null;
			fieldSetFlags()[2] = false;
			return this;
		}

		@Override
		public User build() {
			try {
				User record = new User();
				record.name = fieldSetFlags()[0] ? this.name
						: (java.lang.CharSequence) defaultValue(fields()[0]);
				record.favorite_number = fieldSetFlags()[1] ? this.favorite_number
						: (java.lang.Integer) defaultValue(fields()[1]);
				record.favorite_color = fieldSetFlags()[2] ? this.favorite_color
						: (java.lang.CharSequence) defaultValue(fields()[2]);
				return record;
			}
			catch (Exception e) {
				throw new org.apache.avro.AvroRuntimeException(e);
			}
		}
	}
}
