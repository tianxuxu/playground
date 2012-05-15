package ch.rasc.taffy.controller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class User {

	private final static DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");

	private int id;

	private String name;

	private String surname;

	private String email;

	private LocalDate birthday;

	private String city;

	private String country;

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
