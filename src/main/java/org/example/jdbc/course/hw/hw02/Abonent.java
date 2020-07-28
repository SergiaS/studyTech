package org.example.jdbc.course.hw.hw02;

public class Abonent {
	int id;
	String lastName;
	int number;

	public Abonent(int id, String lastName, int number) {
		this.id = id;
		this.lastName = lastName;
		this.number = number;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Abonent{" +
				"id=" + id +
				", lastName='" + lastName + '\'' +
				", number=" + number +
				'}';
	}
}
