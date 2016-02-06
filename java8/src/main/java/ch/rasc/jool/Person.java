package ch.rasc.jool;

public class Person {
	private final String firstName;
	private final String lastName;
	private final int age;
	private final double height;
	private final double weight;

	public Person(String firstName, String lastName, int age, double height,
			double weight) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.height = height;
		this.weight = weight;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public double getHeight() {
		return height;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", age="
				+ age + ", height=" + height + ", weight=" + weight + "]";
	}


}