package ch.rasc.jpa.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Book {

	@NotNull(groups = Draft.class)
	private String title;

	@NotNull(groups = Draft.class)
	private String author;

	@Min(value = 100, groups = Printing.class)
	private int numOfPages;

	@NotNull(groups = Printing.class)
	@NotEmpty
	private String isbn;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}