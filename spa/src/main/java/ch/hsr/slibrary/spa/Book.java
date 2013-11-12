package ch.hsr.slibrary.spa;

import java.util.Observable;

public class Book extends Observable{

	private String title, author, publisher;
	private Shelf shelf;

	public Book(String name) {
		this.title = name;
	}

	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
        setChanged();
        notifyObservers();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String autor) {
		this.author = autor;
        setChanged();
        notifyObservers();
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
        setChanged();
        notifyObservers();
	}
	
	public Shelf getShelf() {
		return shelf;
	}
	
	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
        setChanged();
        notifyObservers();
	}
	
	@Override
	public String toString() {
		return title + ", " + author + ", " + publisher;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!author.equals(book.author)) return false;
        if (!publisher.equals(book.publisher)) return false;
        if (shelf != book.shelf) return false;
        if (!title.equals(book.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + publisher.hashCode();
        result = 31 * result + (shelf != null ? shelf.hashCode() : 0);
        return result;
    }
}
