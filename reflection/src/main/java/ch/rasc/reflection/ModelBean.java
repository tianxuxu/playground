package ch.rasc.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelBean {
	private final String name;

	private String idProperty;

	private final List<ModelFieldBean> fields;

	private boolean pageing;

	private String readMethod;

	private String createMethod;

	private String updateMethod;

	private String destroyMethod;

	public ModelBean(String name) {
		this.name = name;
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

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public List<ModelFieldBean> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public boolean isPageing() {
		return pageing;
	}

	public void setPageing(boolean pageing) {
		this.pageing = pageing;
	}

	public String getReadMethod() {
		return readMethod;
	}

	public void setReadMethod(String readMethod) {
		this.readMethod = readMethod;
	}

	public String getCreateMethod() {
		return createMethod;
	}

	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod;
	}

	public String getUpdateMethod() {
		return updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	public String getDestroyMethod() {
		return destroyMethod;
	}

	public void setDestroyMethod(String destroyMethod) {
		this.destroyMethod = destroyMethod;
	}

}
