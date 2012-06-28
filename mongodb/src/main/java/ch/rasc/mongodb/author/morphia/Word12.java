package ch.rasc.mongodb.author.morphia;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;

@Entity(noClassnameStored = true)
@Indexes({ @Index("word1,word2") })
public class Word12 {

	@Id
	private ObjectId id;

	private String word1;

	private String word2;

	private int count;

	private List<Word3> word3;

	public ObjectId getId() {
		return id;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord1(final String word1) {
		this.word1 = word1;
	}

	public String getWord2() {
		return word2;
	}

	public void setWord2(final String word2) {
		this.word2 = word2;
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

	public List<Word3> getWord3() {
		return word3;
	}

	public void setWord3(final List<Word3> word3) {
		this.word3 = word3;
	}

}
