package ch.rasc.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelObject {
	private String name;
	private List<Field> fields;

	public ModelObject(String name) {
		this.name = name;
		this.fields = new ArrayList<Field>();
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
