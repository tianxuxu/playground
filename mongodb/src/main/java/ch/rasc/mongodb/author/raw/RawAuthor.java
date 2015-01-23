package ch.rasc.mongodb.author.raw;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.rasc.mongodb.author.Author;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Named
public class RawAuthor implements Author {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				"ch.rasc.mongodb.author")) {
			RawAuthor author = ctx.getBean("author", RawAuthor.class);
			author.writeText(1000);
		}
	}

	@Inject
	private DBCollection collection;

	private final Random random;

	public RawAuthor() {
		this.random = new Random();
	}

	@Override
	public String writeText(int maxWords) {

		int skip = (int) (Math.random() * Math.min(100, this.collection.count()));
		try (DBCursor cursor = this.collection.find().skip(skip)) {
			DBObject start = cursor.next();

			StringBuilder sb = new StringBuilder();
			String w1 = (String) start.get("word1");
			sb.append(w1);
			sb.append(" ");
			String w2 = (String) start.get("word2");
			sb.append(w2);
			sb.append(" ");

			int count = 2;
			int length = sb.length();
			String next = getNext(w1, w2);
			while (next != null && count < maxWords) {
				sb.append(next).append(" ");
				length = length + next.length() + 1;
				if (length > 60) {
					sb.append("\n");
					length = 0;
				}

				w1 = w2;
				w2 = next;
				next = getNext(w1, w2);

				count++;
			}

			return sb.toString();
		}
	}

	private String getNext(String w1, String w2) {
		BasicDBObject query = new BasicDBObject();
		query.append("word1", w1);
		query.append("word2", w2);
		DBObject result = this.collection.findOne(query);

		if (result != null) {
			BasicDBList word3 = (BasicDBList) result.get("word3");

			int total = (Integer) result.get("count");
			int rnd = this.random.nextInt(total) + 1;
			int sum = 0;

			DBObject dbObj = null;
			for (int i = 0; i < word3.size() && sum <= rnd; i++) {
				dbObj = (DBObject) word3.get(i);
				sum += (Integer) dbObj.get("count");
			}

			if (dbObj != null) {
				return (String) dbObj.get("word");
			}

		}

		return null;

	}
}
