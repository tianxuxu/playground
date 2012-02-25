package ch.ralscha.mongodbplayground.author;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.ralscha.mongodbplayground.author.morphia.Word12;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class Sandbox {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext("ch.ralscha.mongodbplayground.author");

		Datastore datastore = ctx.getBean("datastore", Datastore.class);

		Query<Word12> query = datastore.createQuery(Word12.class);
		query.field("word1").equal("w1");
		query.field("word2").equal("w2");
		query.field("word3.word").equal("w3_b");
		UpdateOperations<Word12> op = datastore.createUpdateOperations(Word12.class).inc("word3.$.count");
		datastore.update(query, op);

		//    query = new BasicDBObject();
		//  query.append("word1", w1);
		//  query.append("word2", w2);
		//  query.append("word3.word", w3);
		//  update = new BasicDBObject("$inc", new BasicDBObject("word3.$.count", 1));
		//

		//    Word12 newW = new Word12();
		//    newW.setCount(1);
		//    newW.setWord1("w1");
		//    newW.setWord2("w2");
		//    
		//    List<Word3> word3 = new ArrayList<Word3>();
		//
		//    Word3 w3 = new Word3();
		//    w3.setCount(1);
		//    w3.setWord("w3_a");
		//    word3.add(w3);
		//    
		//    w3 = new Word3();
		//    w3.setCount(1);
		//    w3.setWord("w3_b");
		//    word3.add(w3);
		//
		//    w3 = new Word3();
		//    w3.setCount(1);
		//    w3.setWord("w3_c");
		//    word3.add(w3);
		//    
		//    newW.setWord3(word3);
		//    
		//    datastore.save(newW);
		//    System.out.println(newW.getId());

	}

}
