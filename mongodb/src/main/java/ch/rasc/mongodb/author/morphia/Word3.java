package ch.rasc.mongodb.author.morphia;

import com.google.code.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class Word3 {
	private String word;

	private int count;

	public String getWord() {
		return word;
	}

	public void setWord(final String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}

}
