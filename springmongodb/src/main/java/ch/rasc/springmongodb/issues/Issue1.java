package ch.rasc.springmongodb.issues;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ch.rasc.springmongodb.AppConfig;
import ch.rasc.springmongodb.domain.User;

import com.google.common.collect.Maps;

public class Issue1 {

	public static void main(final String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();

		MongoTemplate mongoTemplate = ctx.getBean(MongoTemplate.class);
		mongoTemplate.dropCollection(User.class);

		User user1 = new User("ralph");
		User user2 = new User("stefan");

		mongoTemplate.save(user1);
		mongoTemplate.save(user2);

		List<User> allUsers = mongoTemplate.findAll(User.class);
		Map<ObjectId, String> idToUserNameMap = Maps.newHashMap();
		for (User user : allUsers) {
			idToUserNameMap.put(user.getId(), user.getUserName());
		}

		for (ObjectId id : idToUserNameMap.keySet()) {
			System.out.println(id);
			User user = mongoTemplate.findOne(Query.query(Criteria.where("id").ne(id)), User.class);
			System.out.println(user);
		}

	}

}
