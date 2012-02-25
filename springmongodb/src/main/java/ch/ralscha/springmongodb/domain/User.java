package ch.ralscha.springmongodb.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private ObjectId id;
	private String userName;

	public User(String userName) {
		this.userName = userName;
	}

	public ObjectId getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + "]";
	}

}
