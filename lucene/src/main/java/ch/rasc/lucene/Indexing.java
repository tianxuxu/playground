package ch.rasc.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class Indexing {

	public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
		Directory directory = new RAMDirectory();
		try (IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_34,
				new WhitespaceAnalyzer(Version.LUCENE_34)))) {

			String[] ids = { "1", "2" };
			String[] unindexed = { "Netherlands", "Italy" };
			String[] unstored = { "Amsterdam has lots of bridges", "Venice has lots of canals" };
			String[] text = { "Amsterdam", "Venice" };

			for (int i = 0; i < ids.length; i++) {
				Document doc = new Document();
				doc.add(new Field("id", ids[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("country", unindexed[i], Field.Store.YES, Field.Index.NO));
				doc.add(new Field("contents", unstored[i], Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("city", text[i], Field.Store.YES, Field.Index.ANALYZED));
				writer.addDocument(doc);
			}

			IndexReader reader = IndexReader.open(writer, true);
			try (IndexSearcher searcher = new IndexSearcher(reader)) {
				Term t = new Term("city", "Amsterdam");
				Query query = new TermQuery(t);
				TopDocs topDocs = searcher.search(query, 1000);
				for (ScoreDoc doc : topDocs.scoreDocs) {
					Document d = searcher.doc(doc.doc);
					System.out.println(d.get("id"));
				}
			}
		}

	}

}
