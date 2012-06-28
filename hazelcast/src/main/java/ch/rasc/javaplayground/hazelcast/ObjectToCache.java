package ch.rasc.javaplayground.hazelcast;

import java.io.Serializable;

public class ObjectToCache implements Serializable {
	private static final long serialVersionUID = -4702483501497968870L;

	private String objectName;

	private String objectValue;

	private Integer objectID;

	public ObjectToCache() {

	}

	public ObjectToCache(final String objectName, final String objectValue, final Integer objectID) {
		super();
		this.objectName = objectName;
		this.objectValue = objectValue;
		this.objectID = objectID;
	}

	public final String getObjectName() {
		return objectName;
	}

	public final void setObjectName(final String objectName) {
		this.objectName = objectName;
	}

	public final String getObjectValue() {
		return objectValue;
	}

	public final void setObjectValue(final String objectValue) {
		this.objectValue = objectValue;
	}

	public final Integer getObjectID() {
		return objectID;
	}

	public final void setObjectID(final Integer objectID) {
		this.objectID = objectID;
	}

	@Override
	public String toString() {
		return "ObjectToCache [objectID=" + objectID + ", objectName=" + objectName + ", objectValue=" + objectValue
				+ "]";
	}

}
