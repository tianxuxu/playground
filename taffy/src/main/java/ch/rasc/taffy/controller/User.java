package ch.rasc.taffy.controller;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User {

	private final static DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormat
			.forPattern("dd/MM/yyyy");

	private final int id;

	private final String name;

	private final String surname;

	private final String email;

	private final LocalDate birthday;

	private final String city;

	private final String country;

	public User(String[] line) {
		this.id = Integer.valueOf(line[0]);
		this.name = line[1];
		this.surname = line[2];
		this.email = line[3];
		this.birthday = LocalDate.parse(line[4], BIRTHDAY_FORMATTER);
		this.city = line[5];
		this.country = line[6];
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	@JsonSerialize(using = BirthdaySerializer.class)
	public LocalDate getBirthday() {
		return birthday;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

}
