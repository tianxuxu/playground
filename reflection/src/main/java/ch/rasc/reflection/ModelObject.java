package ch.rasc.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelObject {
	private final String name;

	private final List<Field> fields;

	public ModelObject(final String name) {
		this.name = name;
		this.fields = new ArrayList<Field>();
	}

	public void addField(final Field field) {
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public List<Field> getFields() {
		return Collections.unmodifiableList(fields);
	}

}
