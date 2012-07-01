package ch.rasc.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelObject {
	private final String name;

	private final List<Field> fields;

	public ModelObject(String name) {
		this.name = name;
		this.fields = new ArrayList<>();
	}

	public void addField(Field field) {
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public List<Field> getFields() {
		return Collections.unmodifiableList(fields);
	}

}
