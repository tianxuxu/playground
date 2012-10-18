package ch.rasc.reflection;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModelBean {
	private String name;

	private String idProperty;

	private Map<String, ModelFieldBean> fields = new LinkedHashMap<>();

	private boolean pageing;

	private String readMethod;

	private String createMethod;

	private String updateMethod;

	private String destroyMethod;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public Map<String, ModelFieldBean> getFields() {
		return fields;
	}

	public void setFields(Map<String, ModelFieldBean> fields) {
		this.fields = fields;
	}

	public void addFields(List<ModelFieldBean> modelFields) {
		for (ModelFieldBean bean : modelFields) {
			fields.put(bean.getName(), bean);
		}
	}

	public ModelFieldBean getField(String fieldName) {
		return fields.get(fieldName);
	}

	public void addField(ModelFieldBean bean) {
		fields.put(bean.getName(), bean);
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
