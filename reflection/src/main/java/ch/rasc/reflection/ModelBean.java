package ch.rasc.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelBean {
	private final String name;

	private final String idProperty;

	private final List<ModelFieldBean> fields;

	public ModelBean(String name, String idProperty) {
		this.name = name;
		this.idProperty = idProperty;
		this.fields = new ArrayList<>();
	}

	public void addField(ModelFieldBean field) {
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public String getIdProperty() {
		return idProperty;
	}

	public List<ModelFieldBean> getFields() {
		return Collections.unmodifiableList(fields);
	}

}
