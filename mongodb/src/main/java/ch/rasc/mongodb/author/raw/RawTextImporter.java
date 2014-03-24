package ch.rasc.mongodb.author.raw;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ch.rasc.mongodb.author.TextExtractor;
import ch.rasc.mongodb.author.TextImporter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

@Named
public class RawTextImporter implements TextImporter {

	@Inject
	private DBCollection collection;

	@Inject
	private TextExtractor extractor;

	@Override
	public void doImport(String fileName) {
		doImport(new File(fileName));
	}

	@Override
	public void doImport(File file) {

		List<String> words = extractor.extractWords(file);

		// ohne Index: 181217 ms
		// mit Index: 8140 ms
		BasicDBObject indexes = new BasicDBObject("word1", 1).append("word2", 1);
		collection.createIndex(indexes);

		for (int i = 0; i < words.size() - 3; i++) {
			String w1 = words.get(i);
			String w2 = words.get(i + 1);
			String w3 = words.get(i + 2);

			// Upsert base document. If exists increment count, otherwise insert
			// document
			BasicDBObject query = new BasicDBObject();
			query.append("word1", w1);
			query.append("word2", w2);
			BasicDBObject update = new BasicDBObject("$inc", new BasicDBObject("count", 1));
			collection.update(query, update, true, false);

			// update count in embedded document
			query = new BasicDBObject();
			query.append("word1", w1);
			query.append("word2", w2);
			query.append("word3.word", w3);
			update = new BasicDBObject("$inc", new BasicDBObject("word3.$.count", 1));

			WriteResult result = collection.update(query, update, false, false);
			if (result.getN() == 0) {

				// add embedded word3 document to the array
				query = new BasicDBObject();
				query.append("word1", w1);
				query.append("word2", w2);

				BasicDBObject word3 = new BasicDBObject();
				word3.append("word", w3);
				word3.append("count", 1);

				collection.update(query, new BasicDBObject("$push", new BasicDBObject("word3", word3)));
			}

		}

	}

}
